package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private GenericRepository<Tag> tagRepository;

    @Autowired
    public TagServiceImpl(GenericRepository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.create(tag);
    }

    @Override
    public Tag getByID(int id) {
        return tagRepository.findById(id);
    }
}
