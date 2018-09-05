package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.config.security.JwtTokenUtil;
import com.teammistique.extensionrepository.data.base.ExtensionRepository;
import com.teammistique.extensionrepository.exceptions.FullFeaturedListException;
import com.teammistique.extensionrepository.exceptions.UnpublishedExtensionException;
import com.teammistique.extensionrepository.models.DTO.ExtensionDTO;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.services.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtensionServiceImpl implements ExtensionService, AdminExtensionService {
    private int maxListSize = 10;
    private static Thread updateManager;
    private static long updateInterval = 600000L;

    private GitHubService gitHubService;
    private ExtensionRepository extensionRepository;
    private StorageService fileService;
    private TagService tagService;
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ExtensionServiceImpl(GitHubService gitHubService, ExtensionRepository extensionRepository, StorageService fileService, TagService tagService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.gitHubService = gitHubService;
        this.extensionRepository = extensionRepository;
        this.fileService = fileService;
        this.tagService = tagService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        setUpdateGitHubPeriod(updateInterval);
    }

    @Override
    public Extension createExtension(ExtensionDTO dto) {

        List<Tag> tags = new ArrayList<>();
        for (String tagName : dto.getTagNames()) {
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
        extension.setCreatedDate(new Date());

        return extensionRepository.create(extension);
    }

    @Override
    public List<Extension> listAllExtensions() {
        return extensionRepository.listAll();
    }

    @Override
    public Extension getExtensionById(int id, String authToken) {
        Extension extension = extensionRepository.findById(id);
        return (extension.getPublishedDate() != null ||
                jwtTokenUtil.isAdmin(authToken) ||
                extension.getOwnerUsername().equals(jwtTokenUtil.getUsernameFromToken(authToken))) ? extension : null;
    }

    @Override
    public Extension updateExtension(ExtensionDTO dto, String authToken) {
        Extension extension = extensionRepository.findById(dto.getId());

        if (!jwtTokenUtil.isAdmin(authToken) && !jwtTokenUtil.getUsernameFromToken(authToken).equals(extension.getOwnerUsername()))
            return null;

        List<Tag> tags = new ArrayList<>();
        for (String tagName : dto.getTagNames()) {
            Tag tag = new Tag(tagName);
            tags.add(tagService.createTag(tag));
        }

        if (!extension.getFile().equals(dto.getFile())) {
            extension.setVersion(extension.getVersion() + 0.1);
            String fileName = getFileNameFromFileLink(extension.getFile());
            fileService.deleteFile(fileName);
            extension.setFile(dto.getFile());
        }

        if (!extension.getImage().equals(dto.getImage())) {
            String imageName = getFileNameFromFileLink(extension.getImage());
            fileService.deleteFile(imageName);
            extension.setImage(dto.getImage());
        }

        extension.setName(dto.getName());
        extension.setDescription(dto.getDescription());
        extension.setLink(dto.getLink());
        extension.setTags(tags);

        return extensionRepository.update(extension);
    }

    @Override
    public void deleteExtension(int id, String authToken) {
        Extension extension = extensionRepository.findById(id);
        boolean admin = jwtTokenUtil.isAdmin(authToken);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        String owner = extension.getOwnerUsername();
        if (admin || username.equals(owner)) {
            fileService.deleteFile(getFileNameFromFileLink(extension.getFile()));
            fileService.deleteFile(getFileNameFromFileLink(extension.getImage()));
            extensionRepository.delete(id);
        }
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
    public Extension changeFeatureStatus(int id) throws FullFeaturedListException, UnpublishedExtensionException {
        Extension extension = extensionRepository.findById(id);
        boolean add = extension.getFeaturedDate() == null;

        if (add && extension.getPublishedDate() == null) {
            throw new UnpublishedExtensionException("Only published extensions can be featured.");
        }

        if (add && listFeaturedExtensions(true).size() >= maxListSize) {
            throw new FullFeaturedListException("Featured list is already full!");
        }

        Date dateToSet = (add) ? new Date() : null;

        extension.setFeaturedDate(dateToSet);
        return extensionRepository.update(extension);
    }

    @Override
    public List<Extension> filterPublishedByName(String name) {
        return extensionRepository.filterPublishedByName(name);
    }

    @Override
    public List<Extension> filterAllByName(String name) {
        return extensionRepository.filterAllByName(name);
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
    public void updateDownloadsCounter(Extension extension) {
        extension.setDownloadsCounter(extension.getDownloadsCounter() + 1);
        extensionRepository.update(extension);
    }

    @Override
    public Extension getExtensionByFile(String fileName) {
        return extensionRepository.getExtensionByFile(fileName);
    }

    @Override
    public Extension changePublishedStatus(int id) {
        Extension extension = extensionRepository.findById(id);
        if (extension.getPublishedDate() != null) {
            extension.setPublishedDate(null);
        } else {
            extension.setPublishedDate(new Date());
            extension.setIssuesCounter(gitHubService.getNumberOfIssues(extension.getLink()));
            extension.setPullRequestsCounter(gitHubService.getNumberOfPullRequests(extension.getLink()));
            extension.setLastCommitDate(gitHubService.getLastCommitDate(extension.getLink()));
        }
        return extensionRepository.update(extension);
    }

    @Override
    public void updateAllGitHubInfo() {
        List<Extension> extensions = listPublishedExtensions(true);
        extensions.forEach(this::updateOneGitHubInfo);
    }

    @Override
    public void updateOneGitHubInfo(Extension extension) {
        String repo = extension.getLink();
        extension.setIssuesCounter(gitHubService.getNumberOfIssues(repo));
        extension.setLastCommitDate(gitHubService.getLastCommitDate(repo));
        extension.setPullRequestsCounter(gitHubService.getNumberOfPullRequests(repo));
        extensionRepository.update(extension);
    }

    @Override
    public void setUpdateGitHubPeriod(long delay) {
        if (updateManager != null) {
            try {
                if (!updateManager.isInterrupted()) updateManager.wait();
                updateManager.interrupt();
            } catch (InterruptedException e) {
                System.out.println("Interrupted syncing thread successfully. Creating new thread.");
                e.printStackTrace();
            }
        }

        updateManager = new Thread(() -> {
            while (true) {
                try {
                    updateAllGitHubInfo();
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateManager.setDaemon(true);
        updateManager.start();
    }

    public int getMaxListSize() {
        return maxListSize;
    }

    public void setMaxListSize(int maxListSize) {
        this.maxListSize = maxListSize;
    }

    private String getFileNameFromFileLink(String file) {
        int index = file.indexOf("downloadFile/") + "downloadFile/".length();
        return file.substring(index);
    }
}