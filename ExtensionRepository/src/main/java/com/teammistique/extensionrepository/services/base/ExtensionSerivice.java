package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.models.Extension;

import java.util.List;

public interface ExtensionSerivice {

    Extension createExtension(Extension extension);

    List<Extension> listAllExtensions();

    Extension getExtensionById(int id);

    Extension updateExtension(Extension extension);

    void deleteExtension(Extension extension);

    List<Extension> listFeaturedExtensions();

    List<Extension> listPopularExtensions();

    List<Extension> listNewExtensions();

    void addFeaturedExtension(Extension extension);

    void removeFeaturedExtension(Extension extension);

    List<Extension> filterByName(String name);

    List<Extension> sortByDownloads();

    List<Extension> sortByName();

    List<Extension> sortByUploadDate();

    List<Extension> sortByLastCommit();

    List<Extension> showPendingExtensions();

    List<Extension> showApprovedExtensions();

    List<Extension> getByUser(String username);

    void approveExtension(int id);
}
