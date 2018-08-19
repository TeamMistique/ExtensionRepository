package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.ExtensionSqlRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Extension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ExtensionServiceImplTest {
    GenericRepository<Extension> mockExtensionRepository = mock(ExtensionSqlRepository.class);

    ExtensionServiceImpl extensionService;

    @Before
    public void setUp() {
        extensionService = new ExtensionServiceImpl(mockExtensionRepository);
    }

    @Test
    public void listAll_shouldReturnAListOfExtensions() {

        //Arrange
        when(mockExtensionRepository.listAll()).thenReturn(
                new ArrayList<Extension>()
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
}