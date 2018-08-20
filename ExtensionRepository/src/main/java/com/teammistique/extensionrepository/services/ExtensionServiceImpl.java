package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.services.base.ExtensionSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtensionServiceImpl implements ExtensionSerivice {
    private int maxListSize = 10;

    private GenericRepository<Extension> extensionRepository;

    @Autowired
    public ExtensionServiceImpl(GenericRepository<Extension> extensionRepository) {
        this.extensionRepository = extensionRepository;
    }

    @Override
    public Extension createExtension(Extension extension) {
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
    public Extension updateExtension(Extension extension) {
        return extensionRepository.update(extension);
    }

    @Override
    public void deleteExtension(Extension extension) {
        extensionRepository.delete(extension);
    }

    @Override
    public List<Extension> listFeaturedExtensions() {
        return listPublishedExtensions().stream()
                .filter(extension -> extension.getFeaturedDate()!=null)
                .sorted(Comparator.comparing(Extension::getFeaturedDate).reversed())
                .limit(maxListSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Extension> listPopularExtensions() {
        return sortByDownloads(listPublishedExtensions()).stream()
                .limit(maxListSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Extension> listNewExtensions() {
        return sortByPublishedDate(listPublishedExtensions()).stream()
                .limit(maxListSize)
                .collect(Collectors.toList());
    }

    @Override
    public void addFeaturedExtension(Extension extension) {
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
        return null;
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
        return null;
    }

    @Override
    public List<Extension> listPublishedExtensions() {
        return listAllExtensions().stream()
                .filter(extension -> extension.getPublishedDate()!=null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Extension> listUnpublishedExtensions() {
        return listAllExtensions().stream()
                .filter(extension -> extension.getPublishedDate()==null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Extension> getByUser(String username) {
        return null;
    }

    @Override
    public void approveExtension(Extension extension) {

    }

    public int getMaxListSize() {
        return maxListSize;
    }

    public void setMaxListSize(int maxListSize) {
        this.maxListSize = maxListSize;
    }
}
