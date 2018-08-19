package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.services.base.ExtensionSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void updateExtension(Extension extension) {

    }

    @Override
    public void deleteExtension(Extension extension) {
        extensionRepository.delete(extension);
    }

    @Override
    public List<Extension> listFeaturedExtensions() {
        return null;
    }

    @Override
    public List<Extension> listPopularExtensions() {
        return extensionRepository.listAll();
    }

    @Override
    public List<Extension> listNewExtensions() {
        return null;
    }

    @Override
    public void addFeaturedExtension(int id) {

    }

    @Override
    public void removeFeaturedExtension(int id) {

    }

    @Override
    public List<Extension> filterByName(String name) {
        return null;
    }

    @Override
    public List<Extension> sortByDownloads() {
        return null;
    }

    @Override
    public List<Extension> sortByName() {
        return null;
    }

    @Override
    public List<Extension> sortByUploadDate() {
        return null;
    }

    @Override
    public List<Extension> sortByLastCommit() {
        return null;
    }

    @Override
    public List<Extension> showPendingExtensions() {
        return null;
    }

    @Override
    public List<Extension> showApprovedExtensions() {
        return null;
    }

    @Override
    public List<Extension> getByUser(String username) {
        return null;
    }

    @Override
    public void approveExtension(int id) {

    }

    public int getMaxListSize() {
        return maxListSize;
    }

    public void setMaxListSize(int maxListSize) {
        this.maxListSize = maxListSize;
    }
}
