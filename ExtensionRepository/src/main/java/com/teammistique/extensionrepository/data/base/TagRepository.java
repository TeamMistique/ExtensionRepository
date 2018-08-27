package com.teammistique.extensionrepository.data.base;

import com.teammistique.extensionrepository.models.Tag;

public interface TagRepository extends GenericRepository<Tag>{
    Tag saveOrUpdate(Tag tag);
}
