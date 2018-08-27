package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/add")
    public List<Tag> addNewTags(@RequestBody List<String> tagNames){
        List<Tag> tags = new ArrayList<>();
        for(String tagName:tagNames){
            Tag tag = new Tag(tagName);
            tags.add(tagService.createTag(tag));
        }
        return tags;
    }
}
