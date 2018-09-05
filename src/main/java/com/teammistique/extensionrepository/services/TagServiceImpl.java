package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.TagRepository;
import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.saveOrUpdate(tag);
    }

    @Override
    public Tag getByName(String tag) {
        return tagRepository.findByName(tag);
    }
}
