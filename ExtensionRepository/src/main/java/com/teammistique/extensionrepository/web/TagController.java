package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    public Tag addNewTag(@ModelAttribute Tag tag){
        return tagService.createTag(tag);
    }
}
