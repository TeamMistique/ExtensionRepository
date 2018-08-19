package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.ExtensionSqlRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Extension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ExtensionServiceImplTest {

    private GenericRepository<Extension> mockExtensionRepository = mock(ExtensionSqlRepository.class);

    private ExtensionServiceImpl extensionService;

    @Before
    public void setUp() {
        extensionService = new ExtensionServiceImpl(mockExtensionRepository);
    }

    @Test
    public void listAll_shouldReturnAListOfExtensions() {

        //Arrange
        when(mockExtensionRepository.listAll()).thenReturn(
                new ArrayList<>()
        );

        //Act
        List<Extension> result = extensionService.listAllExtensions();

        //Assert
        Assert.assertEquals(new ArrayList<Extension>(), result);
    }

    @Test
    public void createExtension_shouldReturnExtension() {

        //Arrange
        Extension extension = new Extension();
        when(mockExtensionRepository.create(extension))
                .thenReturn(extension);

        //Act
        Extension result = extensionService.createExtension(extension);

        //Assert
        Assert.assertSame(extension, result);
    }

    @Test
    public void getExtensionByID_shouldReturnExtension() {

        //Arrange
        int id = 1;
        Extension extension = mock(Extension.class);
        when(extension.getId())
                .thenReturn(id);

        when(mockExtensionRepository.findById(extension.getId()))
                .thenReturn(extension);

        //Act
        Extension result = extensionService.getExtensionById(id);

        //Assert
        Assert.assertSame(extension, result);

    }

    @Test
    public void updateExtension_shouldReturnExtension() {
        //Arrange
        Extension extension = mock(Extension.class);
        when(mockExtensionRepository.update(extension)).thenReturn(extension);
        //Act
        Extension result = extensionService.updateExtension(extension);
        //Assert
        Assert.assertSame(extension, result);
    }

    @Test
    public void addFeaturedExtension_ShouldAddExtensionToLinkedList() {
        //Arrange
        Extension extension = new Extension();
        List<Extension> featuredExtensions = new LinkedList<>();
        //Act
        extensionService.addFeaturedExtension(extension);


    }

    @Test
    public void deleteExtension_shouldCallDeleteMethodFromRepository() {
        Extension extension = new Extension();

        extensionService.deleteExtension(extension);

        verify(mockExtensionRepository).delete(extension);
    }

    @Test
    public void listPopularExtensions_shouldReturnEmptyList_whenThereAreNoExtensions() {
        when(mockExtensionRepository.listAll())
                .thenReturn(new ArrayList<>());

        List<Extension> result = extensionService.listPopularExtensions();

        Assert.assertEquals(new ArrayList<Extension>(), result);
    }

    @Test
    public void listPopularExtension_shouldReturnAllExtensions_whenLessThanMaxListSize() {
        int maxListSize = extensionService.getMaxListSize();
        List<Extension> allExtensions = new ArrayList<>();
        for (int i = 0; i < maxListSize-1; i++) {
            allExtensions.add(new Extension());
        }

        when(mockExtensionRepository.listAll())
                .thenReturn(allExtensions);

        List<Extension> result = extensionService.listPopularExtensions();

        Assert.assertEquals(maxListSize-1, result.size());
    }

    @Test
    public void listPopularExtension_shouldLimitExtensionsResultToMaxListSize_whenAllExtensionsAreMoreThanThat() {
        int maxListSize = extensionService.getMaxListSize();
        List<Extension> allExtensions = new ArrayList<>();
        for (int i = 0; i < maxListSize+5; i++) {
            allExtensions.add(new Extension());
        }
        when(mockExtensionRepository.listAll())
                .thenReturn(allExtensions);

        List<Extension> result = extensionService.listPopularExtensions();

        Assert.assertEquals(maxListSize, result.size());
    }
}