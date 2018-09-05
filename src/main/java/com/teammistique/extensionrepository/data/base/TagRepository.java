package com.teammistique.extensionrepository.data.base;

import com.teammistique.extensionrepository.models.Tag;

import java.util.List;

public interface TagRepository{
    List<Tag> listAll();
    Tag findByName(String name);
    void delete(String name);
    Tag saveOrUpdate(Tag tag);
}
