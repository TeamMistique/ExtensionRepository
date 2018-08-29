package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.exceptions.FullFeaturedListException;
import com.teammistique.extensionrepository.models.DTO.ExtensionDTO;
import com.teammistique.extensionrepository.models.Extension;

import java.util.List;

public interface AdminExtensionService extends ExtensionService{

    List<Extension> listAllExtensions();

    Extension changeFeatureStatus(int id) throws FullFeaturedListException;

    List<Extension> filterAllByName(String name);

    Extension publishExtension(int id);

    void setMaxListSize(int maxListSize);

    void disableUser(int id);
}
