package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.TagSqlRepository;
import com.teammistique.extensionrepository.data.base.TagRepository;
import com.teammistique.extensionrepository.models.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TagServiceImplTest {

    private TagRepository mockTagRepository = mock(TagRepository.class);

    private TagServiceImpl tagService;

    @Before
    public void setUp() {
        tagService = new TagServiceImpl(mockTagRepository);
    }

    @Test
    public void createTag_shouldReturnTag() {
        Tag tag = new Tag();
        when(mockTagRepository.saveOrUpdate(tag)).thenReturn(tag);

        Tag result = tagService.createTag(tag);

        Assert.assertSame(tag, result);
    }

    @Test
    public void getByID_shouldCallRepoMethod() {
        String name = "name";
        when(mockTagRepository.findByName(name)).thenReturn(new Tag(name));
        Tag tag = tagService.getByName(name);
        verify(mockTagRepository).findByName(name);
    }
}
