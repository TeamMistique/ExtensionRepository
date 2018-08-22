package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.TagSqlRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TagServiceImplTest {

    private GenericRepository<Tag> mockTagRepository = mock(TagSqlRepository.class);

    private TagServiceImpl tagService;

    @Before
    public void setUp() {
        tagService = new TagServiceImpl(mockTagRepository);
    }

    @Test
    public void createTag_shouldReturnTag() {
        Tag tag = new Tag();
        when(mockTagRepository.create(tag)).thenReturn(tag);

        Tag result = tagService.createTag(tag);

        Assert.assertSame(tag, result);

    }






}