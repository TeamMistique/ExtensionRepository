package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.models.DTO.TagDTO;
import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    public Tag addNewTags(@RequestBody List<TagDTO> tags){
        for(TagDTO dto:tags){
            Tag tag = new Tag(dto.getTagName());
            tagService.createTag(tag);
        }
    }
}
