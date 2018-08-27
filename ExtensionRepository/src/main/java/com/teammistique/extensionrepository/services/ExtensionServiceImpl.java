package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.ExtensionRepository;
import com.teammistique.extensionrepository.exceptions.FullFeaturedListException;
import com.teammistique.extensionrepository.models.DTO.ExtensionDTO;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.services.base.ExtensionService;
import com.teammistique.extensionrepository.services.base.GitHubService;
import com.teammistique.extensionrepository.services.base.TagService;
import com.teammistique.extensionrepository.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtensionServiceImpl implements ExtensionService {
    private int maxListSize = 10;

    private GitHubService gitHubService;
    private ExtensionRepository extensionRepository;
    private TagService tagService;
    private UserService userService;

    @Autowired
    public ExtensionServiceImpl(GitHubService gitHubService, ExtensionRepository extensionRepository, TagService tagService, UserService userService) {
        this.gitHubService = gitHubService;
        this.extensionRepository = extensionRepository;
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public Extension createExtension(ExtensionDTO dto) {
        List<Tag> tags = new ArrayList<>();
        for(String tagName:dto.getTagNames()){
            Tag tag = new Tag(tagName);
            tags.add(tagService.createTag(tag));
        }

        Extension extension = new Extension();
        extension.setOwner(userService.findOne(dto.getUsername()));
        extension.setName(dto.getName());
        extension.setDescription(dto.getDescription());
        extension.setLink(dto.getLink());
        extension.setFile(dto.getFile());
        extension.setImage(dto.getImage());
        extension.setTags(tags);

        extension.setIssuesCounter(gitHubService.getNumberOfIssues(extension.getLink()));
        extension.setPullRequestsCounter(gitHubService.getNumberOfPullRequests(extension.getLink()));
        extension.setLastCommitDate(gitHubService.getLastCommitDate(extension.getLink()));
        extension.setCreatedDate(new Date());

        return extensionRepository.create(extension);
    }

    @Override
    public List<Extension> listAllExtensions() {
        return extensionRepository.listAll();
    }

    @Override
    public Extension getExtensionById(int id) {
        return extensionRepository.findById(id);
    }

    @Override
    public Extension updateExtension(ExtensionDTO dto) {
        List<Tag> tags = new ArrayList<>();
        for(String tagName:dto.getTagNames()){
            Tag tag = new Tag(tagName);
            tags.add(tagService.createTag(tag));
        }

        Extension extension = extensionRepository.findById(dto.getId());
        extension.setName(dto.getName());
        extension.setDescription(dto.getDescription());
        extension.setLink(dto.getLink());
        extension.setFile(dto.getFile());
        extension.setImage(dto.getImage());
        extension.setTags(tags);

        return extensionRepository.update(extension);
    }

    @Override
    public void deleteExtension(Extension extension) {
        extensionRepository.delete(extension);
    }

    @Override
    public List<Extension> listFeaturedExtensions(boolean featured) {
        return extensionRepository.listFeaturedExtensions(featured);
    }

    @Override
    public List<Extension> listPopularExtensions() {
        return extensionRepository.listPopularExtensions(maxListSize);
    }

    @Override
    public List<Extension> listNewExtensions() {
        return extensionRepository.listNewExtensions(maxListSize);
    }

    @Override
    public void addFeaturedExtension(Extension extension) throws FullFeaturedListException {
        if (listFeaturedExtensions(true).size() >= maxListSize) {
            throw new FullFeaturedListException("Featured list is already full!");
        }
        extension.setFeaturedDate(new Date());
        extensionRepository.update(extension);
    }

    @Override
    public void removeFeaturedExtension(Extension extension) {
        extension.setFeaturedDate(null);
        extensionRepository.update(extension);
    }

    @Override
    public List<Extension> filterByName(String name) {
        return extensionRepository.filterByName(name);
    }

    @Override
    public List<Extension> sortByDownloads(List<Extension> extensions) {
        return extensions.stream()
                .sorted(Comparator.comparing(Extension::getDownloadsCounter).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Extension> sortByName(List<Extension> extensions) {
        return extensions.stream()
                .sorted(Comparator.comparing(Extension::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Extension> sortByPublishedDate(List<Extension> extensions) {
        return extensions.stream()
                .sorted(Comparator.comparing(Extension::getPublishedDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Extension> sortByLastCommit(List<Extension> extensions) {
        return extensions.stream()
                .sorted(Comparator.comparing(Extension::getLastCommitDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Extension> listPublishedExtensions(boolean published) {
        return extensionRepository.listPublishedExtensions(published);
    }

    @Override
    public void publishExtension(Extension extension) {
        Extension published = extensionRepository.findById(extension.getId());
        published.setPublishedDate(new Date());
        extensionRepository.update(published);
    }

    public int getMaxListSize() {
        return maxListSize;
    }

    public void setMaxListSize(int maxListSize) {
        this.maxListSize = maxListSize;
    }
}
