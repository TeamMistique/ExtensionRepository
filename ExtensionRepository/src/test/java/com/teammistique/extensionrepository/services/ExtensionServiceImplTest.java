package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.ExtensionSqlRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Extension;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Array;
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
        when(extension.getId()).thenReturn(id);

        when(mockExtensionRepository.findById(extension.getId()))
                .thenReturn(extension);

        Extension result = extensionService.getExtensionById(id);

        Assert.assertSame(extension, result);

    }

    @Test
    public void updateExtension_shouldReturnExtension() {
        Extension extension = new Extension();
        when(mockExtensionRepository.update(extension)).thenReturn(extension);

        Extension result = extensionService.updateExtension(extension);

        Assert.assertSame(extension, result);
    }

    @Test
    public void addFeaturedExtension_shouldUpdateExtension() {
        Extension extension = new Extension();
        when(mockExtensionRepository.update(extension)).thenReturn(extension);

        extensionService.addFeaturedExtension(extension);

        Assert.assertTrue((new Date().getTime()- extension.getFeaturedDate().getTime()) < 1000);
    }

    @Test
    public void removeFeaturedExtension_shouldUpdateExtension() {
        Extension extension = new Extension();
        extension.setFeaturedDate(new Date());
        when(mockExtensionRepository.update(extension)).thenReturn(extension);

        extensionService.removeFeaturedExtension(extension);

        Assert.assertNull(extension.getFeaturedDate());
    }

    @Test
    public void listFeaturedExtension_shouldReturnEmptyList_whenThereAreNoFeaturedExtensions() {
        when(mockExtensionRepository.listAll()).thenReturn(new ArrayList<>());

        List<Extension> result = extensionService.listFeaturedExtensions();

        Assert.assertEquals(new ArrayList<Extension>(), result);
    }

    @Test
    public void listFeaturedExtension_shouldReturnAllFeaturedExtensions_whenLessThanMaxListSize() {
        int maxListSize = extensionService.getMaxListSize();
        List<Extension> allPublished = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(allPublished, maxListSize - 1);

        for (Extension extension : allPublished) {
            extension.setFeaturedDate(new Date());
        }

        when(mockExtensionRepository.listAll())
                .thenReturn(allPublished);

        List<Extension> result = extensionService.listFeaturedExtensions();

        Assert.assertEquals(maxListSize - 1, result.size());
    }

    @Test
    public void listFeatureExtensions_shouldLimitFeatureExtensionsResultToMaxListSize_whenAllFeaturedExtensionsAreMoreThanThat() {
        int maxListSize = extensionService.getMaxListSize();
        List<Extension> allFeaturedExtensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(allFeaturedExtensions, maxListSize + 5);

        for (Extension extension : allFeaturedExtensions) {
            extension.setFeaturedDate(new Date());
        }

        when(mockExtensionRepository.listAll())
                .thenReturn(allFeaturedExtensions);

        List<Extension> result = extensionService.listFeaturedExtensions();

        Assert.assertEquals(maxListSize, result.size());
    }

    @Test
    public void listFeaturedExtensions_shouldReturnOnlyFeaturedExtensions() {
        List<Extension> allPublishedExtensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(allPublishedExtensions, 7);
        for (int i = 0; i < 3; i++) {
          allPublishedExtensions.get(i).setFeaturedDate(new Date());
        }
        when(mockExtensionRepository.listAll()).thenReturn(allPublishedExtensions);

        List<Extension> result = extensionService.listFeaturedExtensions();

        Assert.assertEquals(3, result.size());
        for (Extension extension : result) {
            Assert.assertNotNull(extension.getFeaturedDate());
        }
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
    public void listNewExtensions_shouldReturnEmptyList_whenNoExtensions() {
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
    public void listPublishedExtensions_shouldReturnOnlyPublishedExtensions() {
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
        List<Date> dates = Arrays.asList(new Date(1534692315464L),new Date(1341123762001L),new Date(1399129992001L));
        List<Extension> extensions = new ArrayList<>();
        Helpers.fillListWithUnpublishedExtensions(extensions, 3);
        for (int i = 0; i < extensions.size(); i++) {
            extensions.get(i).setPublishedDate(dates.get(i));
        }
        dates.sort(Comparator.reverseOrder());

        extensions = extensionService.sortByPublishedDate(extensions);

        for (int i = 0; i < extensions.size(); i++) {
            Assert.assertEquals(dates.get(i), extensions.get(i).getPublishedDate());
        }
    }

    @Test
    public void sortByDownloads_shouldSortByNumberOfDownloadsInReverseOrder() {
        int[] downloads = {5, 7, 120, 32, 42, 2, 34};
        List<Extension> extensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(extensions, 7);
        for (int i = 0; i < extensions.size(); i++) {
            extensions.get(i).setDownloadsCounter(downloads[i]);
        }
        Arrays.sort(downloads);

        extensions = extensionService.sortByDownloads(extensions);

        for (int i = 0; i < extensions.size(); i++) {
            Assert.assertEquals(downloads[6-i], extensions.get(i).getDownloadsCounter());
        }
    }

    @Test
    public void sortByName_shouldSortByNameInAlphabeticalOrder() {
        List<String> names = Arrays.asList("Gosho", "Pesho", "Misho", "Anna", "Banana");
        List<Extension> extensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(extensions, 5);
        for (int i = 0; i < extensions.size(); i++) {
            extensions.get(i).setName(names.get(i));
        }
        Collections.sort(names);

        extensions = extensionService.sortByName(extensions);

        for (int i = 0; i < extensions.size(); i++) {
            Assert.assertEquals(names.get(i), extensions.get(i).getName());
        }
    }

    @Test
    public void listUnpublishedExtensions_shouldReturnEmptyList_whenThereAreNoExtensions() {
        when(mockExtensionRepository.listAll()).thenReturn(new ArrayList<>());

        List<Extension> result = extensionService.listUnpublishedExtensions();

        Assert.assertEquals(new ArrayList<Extension>(), result);
    }

    @Test
    public void listUnpublishedExtensions_shouldReturnEmptyList_whenAllExtensionsHaveBeenPublished() {
        List<Extension> publishedExtensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(publishedExtensions, 8);
        when(mockExtensionRepository.listAll()).thenReturn(publishedExtensions);

        List<Extension> result = extensionService.listUnpublishedExtensions();

        Assert.assertEquals(new ArrayList<Extension>(), result);
    }

    @Test
    public void listUnpublishedExtensions_shouldReturnOnlyUnpublishedExtensions() {
        List<Extension> extensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(extensions, 4);
        Helpers.fillListWithUnpublishedExtensions(extensions, 6);
        Helpers.fillListWithPublishedExtensions(extensions, 3);
        Helpers.fillListWithUnpublishedExtensions(extensions, 2);
        when(mockExtensionRepository.listAll()).thenReturn(extensions);

        List<Extension> result = extensionService.listUnpublishedExtensions();

        Assert.assertEquals(8, result.size());
        for(Extension extension:result){
            Assert.assertNull(extension.getPublishedDate());
        }
    }

    @Test
    public void sortByLastCommit_shouldSortByLastCommitDateInReverseOrder() {
        List<Date> commitDates = Arrays.asList(new Date(544765595L), new Date(1447655953L), new Date(1544765595L),
                new Date(1437655953L), new Date(1448655953L), new Date(1534754595L), new Date(1484655953L), new Date(1684655953L));

        List<Extension> extensions = new ArrayList<>();
        Helpers.fillListWithPublishedExtensions(extensions, 8);
        for (int i = 0; i < extensions.size(); i++) {
            extensions.get(i).setLastCommitDate(commitDates.get(i));
        }
        commitDates.sort(Comparator.reverseOrder());

        extensions = extensionService.sortByLastCommit(extensions);

        for (int i = 0; i < extensions.size(); i++) {
            Assert.assertEquals(commitDates.get(i), extensions.get(i).getLastCommitDate());
        }
    }

    private static class Helpers {
        private static void fillListWithPublishedExtensions(List<Extension> list, int count) {
            for (int i = 0; i < count; i++) {
                Extension extension = new Extension();
                extension.setPublishedDate(new Date());
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