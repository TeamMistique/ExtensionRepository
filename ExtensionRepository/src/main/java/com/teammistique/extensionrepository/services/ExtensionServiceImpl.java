package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.services.base.ExtensionSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtensionServiceImpl implements ExtensionSerivice {

    GenericRepository<Extension> extensionRepository;

    @Autowired
    public ExtensionServiceImpl(GenericRepository<Extension> extensionRepository) {
        this.extensionRepository = extensionRepository;
    }

    @Override
    public void createExtension(Extension extension) {

    }

    @Override
    public List<Extension> listAllExtensions() {
        return null;
    }

    @Override
    public Extension getExtensionById(int id) {
        return null;
    }

    @Override
    public void updateExtension(Extension extension) {

    }

    @Override
    public void deleteExtension(int id) {

    }

    @Override
    public List<Extension> listFeaturedExtensions() {
        return null;
    }

    @Override
    public List<Extension> listPopularExtensions() {
        return null;
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
    public void approveExtension(int id) {

    }
}
