package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.exceptions.FullFeaturedListException;
import com.teammistique.extensionrepository.models.Extension;

import java.util.List;

public interface ExtensionSerivice {

    Extension createExtension(Extension extension);

    List<Extension> listAllExtensions();

    Extension getExtensionById(int id);

    Extension updateExtension(Extension extension);

    void deleteExtension(Extension extension);

    List<Extension> listFeaturedExtensions(boolean featured);

    List<Extension> listPopularExtensions();

    List<Extension> listNewExtensions();

    void addFeaturedExtension(Extension extension) throws FullFeaturedListException;

    void removeFeaturedExtension(Extension extension);

    List<Extension> filterByName(String name);

    List<Extension> sortByDownloads(List<Extension> extensions);

    List<Extension> sortByName(List<Extension> extensions);

    List<Extension> sortByPublishedDate(List<Extension> extensions);

    List<Extension> sortByLastCommit(List<Extension> extensions);

    List<Extension> listPublishedExtensions(boolean published);

    List<Extension> getByUser(String username);

    void approveExtension(Extension extension);
}
