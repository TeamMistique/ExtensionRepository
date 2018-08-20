package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.ExtensionSqlRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Extension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        when(mockExtensionRepository.listAll()).thenReturn(
                new ArrayList<>()
        );

        List<Extension> result = extensionService.listAllExtensions();

        Assert.assertEquals(new ArrayList<Extension>(), result);
    }

    @Test
    public void createExtension_shouldReturnExtension() {
        Extension extension = new Extension();
        when(mockExtensionRepository.create(extension))
                .thenReturn(extension);

        Extension result = extensionService.createExtension(extension);

        Assert.assertSame(extension, result);
    }

    @Test
    public void getExtensionByID_shouldReturnExtension() {
        int id = 1;
        Extension extension = mock(Extension.class);
        when(extension.getId())
                .thenReturn(id);

        when(mockExtensionRepository.findById(extension.getId()))
                .thenReturn(extension);

        Extension result = extensionService.getExtensionById(id);

        Assert.assertSame(extension, result);

    }

    @Test
    public void updateExtension_shouldReturnExtension() {
        Extension extension = mock(Extension.class);
        when(mockExtensionRepository.update(extension)).thenReturn(extension);

        Extension result = extensionService.updateExtension(extension);

        Assert.assertSame(extension, result);
    }

    @Test
    public void addFeaturedExtension_ShouldUpdateExtension() {

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
    public void listPopularExtensions_shouldReturnAllExtensions_whenLessThanMaxListSize() {
        int maxListSize = extensionService.getMaxListSize();
        List<Extension> allExtensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(allExtensions, maxListSize - 1);

        when(mockExtensionRepository.listAll())
                .thenReturn(allExtensions);

        List<Extension> result = extensionService.listPopularExtensions();

        Assert.assertEquals(maxListSize - 1, result.size());
    }

    @Test
    public void listPopularExtensions_shouldLimitExtensionsResultToMaxListSize_whenAllExtensionsAreMoreThanThat() {
        int maxListSize = extensionService.getMaxListSize();
        List<Extension> allExtensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(allExtensions, maxListSize + 5);
        when(mockExtensionRepository.listAll())
                .thenReturn(allExtensions);

        List<Extension> result = extensionService.listPopularExtensions();

        Assert.assertEquals(maxListSize, result.size());
    }

    @Test
    public void listNewExtesions_shouldReturnEmptyList_whenNoExtensions() {
        when(mockExtensionRepository.listAll())
                .thenReturn(new ArrayList<>());

        List<Extension> result = extensionService.listNewExtensions();

        Assert.assertEquals(new ArrayList<Extension>(), result);
    }

    @Test
    public void listNewExtension_shouldReturnAllExtensions_whenLessThanMaxListSize() {
        int maxListSize = extensionService.getMaxListSize();
        List<Extension> allExtensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(allExtensions, maxListSize - 1);

        when(mockExtensionRepository.listAll())
                .thenReturn(allExtensions);

        List<Extension> result = extensionService.listNewExtensions();

        Assert.assertEquals(maxListSize - 1, result.size());
    }

    @Test
    public void listNewExtensions_shouldLimitExtensionsToMaxListSize_whenAllExtensionsAreMoreThanThat() {
        int maxListSize = extensionService.getMaxListSize();
        List<Extension> allExtensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(allExtensions, maxListSize+5);

        when(mockExtensionRepository.listAll())
                .thenReturn(allExtensions);

        List<Extension> result = extensionService.listNewExtensions();

        Assert.assertEquals(maxListSize, result.size());
    }

    @Test
    public void listPublishedExtensions_shouldReturnEmptyList_whenNoExtensionsHaveBeenPublished() {
        List<Extension> unpublishedExtensions = new ArrayList<>();
        Helpers.fillListWithUnpublishedExtensions(unpublishedExtensions, 5);
        when(mockExtensionRepository.listAll()).thenReturn(unpublishedExtensions);

        List<Extension> result = extensionService.listPublishedExtensions();

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void listPublishedExtensions_shouldReturnOnlyPublishedExtesnions() {
        List<Extension> allExtensions = new ArrayList<>();
        Helpers.fillListWithUnpublishedExtensions(allExtensions, 5);
        Helpers.fillListWithPublishedExtensions(allExtensions, 3);
        when(mockExtensionRepository.listAll()).thenReturn(allExtensions);

        List<Extension> result = extensionService.listPublishedExtensions();

        Assert.assertEquals(3, result.size());
        for (Extension extension : result) {
            Assert.assertNotNull(extension.getPublishedDate());
        }
    }

    @Test
    public void sortByPublishedDate_shouldSortByPublishedDateInReverseOrder() {
        Extension extension1 = mock(Extension.class);
        Extension extension2 = mock(Extension.class);
        Extension extension3 = mock(Extension.class);

        when(extension1.getPublishedDate()).thenReturn(new Date(1534692315464L)); //2018
        when(extension2.getPublishedDate()).thenReturn(new Date(1341123762001L)); //2012
        when(extension3.getPublishedDate()).thenReturn(new Date(1399129992001L)); //2014

        List<Extension> allExtensions = Arrays.asList(extension1, extension2, extension3);
        List<Extension> orderedExtensions = Arrays.asList(extension1, extension3, extension2);

        List<Extension> result = extensionService.sortByPublishedDate(allExtensions);

        for (int i = 0; i < result.size(); i++) {
            Assert.assertSame(orderedExtensions.get(i), result.get(i));
        }
    }

    @Test
    public void sortByDownloads_shouldSortByNumberOfDownloadsInReverseOrder() {
        Extension extension1 = mock(Extension.class);
        Extension extension2 = mock(Extension.class);
        Extension extension3 = mock(Extension.class);

        when(extension1.getDownloadsCounter()).thenReturn(5);
        when(extension2.getDownloadsCounter()).thenReturn(17);
        when(extension3.getDownloadsCounter()).thenReturn(2);

        List<Extension> allExtensions = Arrays.asList(extension1, extension2, extension3);
        List<Extension> orderedExtensions = Arrays.asList(extension2, extension1, extension3);

        List<Extension> result = extensionService.sortByDownloads(allExtensions);

        for (int i = 0; i < result.size(); i++) {
            Assert.assertSame(orderedExtensions.get(i), result.get(i));
        }
    }

    @Test
    public void sortByName_shouldSortByNameInAlphabeticalOrder() {
        List<String> names = Arrays.asList("Gosho", "Pesho", "Misho", "Anna", "Banana");
        List<Extension> extensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(extensions, 5);
        for (int i = 0; i < extensions.size(); i++) {
            when(extensions.get(i).getName()).thenReturn(names.get(i));
        }
        Collections.sort(names);

        extensions = extensionService.sortByName(extensions);

        for (int i = 0; i < extensions.size(); i++) {
            Assert.assertEquals(names.get(i), extensions.get(i).getName());
        }
    }









    private static class Helpers {
        private static void fillListWithPublishedExtensions(List<Extension> list, int count) {
            for (int i = 0; i < count; i++) {
                Extension extension = mock(Extension.class);
                when(extension.getPublishedDate()).thenReturn(new Date());
                list.add(extension);
            }
        }

        private static void fillListWithUnpublishedExtensions(List<Extension> list, int count) {
            for (int i = 0; i < count; i++) {
                list.add(new Extension());
            }
        }
    }
}