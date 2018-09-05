package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.models.Tag;

public interface TagService {
   Tag createTag(Tag tag);
   Tag getByName(String tag);
}
