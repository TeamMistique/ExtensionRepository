package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.exceptions.FullFeaturedListException;
import com.teammistique.extensionrepository.models.DTO.ExtensionDTO;
import com.teammistique.extensionrepository.models.Extension;

import java.util.List;

public interface ExtensionService {

    Extension createExtension(ExtensionDTO dto);

    List<Extension> listAllExtensions();

    Extension getExtensionById(int id);

    Extension updateExtension(ExtensionDTO dto);

    void deleteExtension(Extension extension);

    List<Extension> listFeaturedExtensions(boolean featured);

    List<Extension> listPopularExtensions();

    List<Extension> listNewExtensions();

    Extension changeFeatureStatus(int id) throws FullFeaturedListException;

    List<Extension> filterPublishedByName(String name);

    List<Extension> filterAllByName(String name);

    List<Extension> sortByDownloads(List<Extension> extensions);

    List<Extension> sortByName(List<Extension> extensions);

    List<Extension> sortByPublishedDate(List<Extension> extensions);

    List<Extension> sortByLastCommit(List<Extension> extensions);

    List<Extension> listPublishedExtensions(boolean published);

    Extension publishExtension(int id);

    void setMaxListSize(int maxListSize);
}
