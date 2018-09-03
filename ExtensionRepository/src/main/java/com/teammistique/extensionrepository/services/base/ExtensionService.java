package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.exceptions.InvalidLinkException;
import com.teammistique.extensionrepository.models.DTO.ExtensionDTO;
import com.teammistique.extensionrepository.models.Extension;

import java.util.List;

public interface ExtensionService {

    Extension createExtension(ExtensionDTO dto) throws InvalidLinkException;

    Extension getExtensionById(int id, String authToken);

    Extension updateExtension(ExtensionDTO dto, String authToken);

    void deleteExtension(int id, String authToken);

    List<Extension> listFeaturedExtensions(boolean featured);

    List<Extension> listPopularExtensions();

    List<Extension> listNewExtensions();

    List<Extension> filterPublishedByName(String name);

    List<Extension> sortByDownloads(List<Extension> extensions);

    List<Extension> sortByName(List<Extension> extensions);

    List<Extension> sortByPublishedDate(List<Extension> extensions);

    List<Extension> sortByLastCommit(List<Extension> extensions);

    List<Extension> listPublishedExtensions(boolean published);

    void updateDownloadsCounter(Extension extension);

    Extension getExtensionByFile(String fileName);

    void updateGitHub();
}
