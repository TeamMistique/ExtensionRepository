package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.ImageSqlRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    private GenericRepository<Image> mockImageRepository = mock(ImageSqlRepository.class);

    private ImageServiceImpl imageService;

    @Before
    public void setUp() {
      imageService = new ImageServiceImpl(mockImageRepository);
    }

    @Test
    public void createImage_shouldReturnImage() {
        Image image = new Image();
        when(mockImageRepository.create(image)).thenReturn(image);

        Image result = imageService.createImage(image);

        Assert.assertSame(image, result);
    }

    @Test
    public void findImageByID_shouldReturnImage() {
        int id = 1;
        Image image = mock(Image.class);
        when(image.getId()).thenReturn(id);

        when(mockImageRepository.findById(image.getId()))
                .thenReturn(image);

        Image result = imageService.getImageById(id);

        Assert.assertSame(image, result);

    }







}
