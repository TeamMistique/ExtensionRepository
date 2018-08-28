package com.teammistique.extensionrepository.data.base;

import com.teammistique.extensionrepository.models.Extension;

import java.util.List;

public interface ExtensionRepository extends GenericRepository<Extension>{
    List<Extension> listPublishedExtensions(boolean published);
    List<Extension> listFeaturedExtensions(boolean featured);
    List<Extension> filterPublishedByName(String name);
    List<Extension> filterAllByName(String name);
    List<Extension> listPopularExtensions(int count);
    List<Extension> listNewExtensions(int count);
}
