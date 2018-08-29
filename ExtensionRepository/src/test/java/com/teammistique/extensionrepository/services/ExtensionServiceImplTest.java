//package com.teammistique.extensionrepository.services;
//
//import com.teammistique.extensionrepository.data.ExtensionSqlRepository;
//import com.teammistique.extensionrepository.data.base.ExtensionRepository;
//import com.teammistique.extensionrepository.exceptions.FullFeaturedListException;
//import com.teammistique.extensionrepository.models.Extension;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.*;
//
//import static org.mockito.Mockito.*;
//
//public class ExtensionServiceImplTest {
//
//    private ExtensionRepository mockExtensionRepository = mock(ExtensionSqlRepository.class);
//
//    private ExtensionServiceImpl extensionService;
//
//    @Before
//    public void setUp() {
//        extensionService = new ExtensionServiceImpl(mockExtensionRepository);
//    }
//
//    @Test
//    public void listAll_shouldReturnAListOfExtensions() {
//        when(mockExtensionRepository.listAll()).thenReturn(
//                new ArrayList<>()
//        );
//
//        List<Extension> result = extensionService.listAllExtensions();
//
//        Assert.assertEquals(new ArrayList<Extension>(), result);
//    }
//
//    @Test
//    public void createExtension_shouldReturnExtension() {
//        Extension extension = new Extension();
//        when(mockExtensionRepository.create(extension))
//                .thenReturn(extension);
//
//        Extension result = extensionService.createExtension(extension);
//
//        Assert.assertSame(extension, result);
//    }
//
//    @Test
//    public void getExtensionByID_shouldReturnExtension() {
//        int id = 1;
//        Extension extension = mock(Extension.class);
//        when(extension.getId()).thenReturn(id);
//
//        when(mockExtensionRepository.findById(extension.getId()))
//                .thenReturn(extension);
//
//        Extension result = extensionService.getExtensionById(id);
//
//        Assert.assertSame(extension, result);
//
//    }
//
//    @Test
//    public void updateExtension_shouldReturnExtension() {
//        Extension extension = new Extension();
//        when(mockExtensionRepository.update(extension)).thenReturn(extension);
//
//        Extension result = extensionService.updateExtension(extension);
//
//        Assert.assertSame(extension, result);
//    }
//
//    @Test
//    public void addFeaturedExtension_shouldUpdateExtension() throws FullFeaturedListException {
//        Extension extension = new Extension();
//        when(mockExtensionRepository.update(extension)).thenReturn(extension);
//
//        extensionService.changeFeatureStatus(extension);
//
//        Assert.assertTrue((new Date().getTime()- extension.getFeaturedDate().getTime()) < 1000);
//    }
//
//    @Test(expected = FullFeaturedListException.class)
//    public void addFeaturedExtension_shouldThrowExceptionWhenMaxListSize() throws FullFeaturedListException {
//        List<Extension> extensions = new ArrayList<>();
//        Helpers.fillListWithPublishedExtensions(extensions, extensionService.getMaxListSize());
//        when(mockExtensionRepository.listFeaturedExtensions(true)).thenReturn(extensions);
//        Extension extension = new Extension();
//        extensionService.changeFeatureStatus(extension);
//    }
//
//
//    @Test
//    public void removeFeaturedExtension_shouldUpdateExtension() {
//        Extension extension = new Extension();
//        extension.setFeaturedDate(new Date());
//        when(mockExtensionRepository.update(extension)).thenReturn(extension);
//
//        extensionService.removeFeaturedExtension(extension);
//
//        Assert.assertNull(extension.getFeaturedDate());
//    }
//
//    @Test
//    public void listFeaturedExtension_shouldCallRepositoryMethod() {
//        extensionService.listFeaturedExtensions(true);
//        verify(mockExtensionRepository).listFeaturedExtensions(true);
//
//        extensionService.listFeaturedExtensions(false);
//        verify(mockExtensionRepository).listFeaturedExtensions(false);
//    }
//
//
//    @Test
//    public void deleteExtension_shouldCallRepositoryMethod() {
//        Extension extension = new Extension();
//
//        extensionService.deleteExtension(extension);
//
//        verify(mockExtensionRepository).delete(extension);
//    }
//
//    @Test
//    public void listPopularExtensions_shouldCallRepositoryMethod(){
//        extensionService.listPopularExtensions();
//
//        verify(mockExtensionRepository).listPopularExtensions(extensionService.getMaxListSize());
//    }
//
//    @Test
//    public void listNewExtensions_shouldCallRepositoryMethod() {
//        extensionService.listNewExtensions();
//
//        verify(mockExtensionRepository).listNewExtensions(extensionService.getMaxListSize());
//    }
//
//    @Test
//    public void listPublishedExtensions_shouldCallRepositoryMethod() {
//        extensionService.listPublishedExtensions(true);
//        verify(mockExtensionRepository).listPublishedExtensions(true);
//
//        extensionService.listPublishedExtensions(false);
//        verify(mockExtensionRepository).listPublishedExtensions(false);
//    }
//
//    @Test
//    public void sortByPublishedDate_shouldSortByPublishedDateInReverseOrder() {
//        List<Date> dates = Arrays.asList(new Date(1534692315464L),new Date(1341123762001L),new Date(1399129992001L));
//        List<Extension> extensions = new ArrayList<>();
//        Helpers.fillListWithUnpublishedExtensions(extensions, 3);
//        for (int i = 0; i < extensions.size(); i++) {
//            extensions.get(i).setPublishedDate(dates.get(i));
//        }
//        dates.sort(Comparator.reverseOrder());
//
//        extensions = extensionService.sortByPublishedDate(extensions);
//
//        for (int i = 0; i < extensions.size(); i++) {
//            Assert.assertEquals(dates.get(i), extensions.get(i).getPublishedDate());
//        }
//    }
//
//    @Test
//    public void sortByDownloads_shouldSortByNumberOfDownloadsInReverseOrder() {
//        int[] downloads = {5, 7, 120, 32, 42, 2, 34};
//        List<Extension> extensions = new ArrayList<>();
//        Helpers.fillListWithPublishedExtensions(extensions, 7);
//        for (int i = 0; i < extensions.size(); i++) {
//            extensions.get(i).setDownloadsCounter(downloads[i]);
//        }
//        Arrays.sort(downloads);
//
//        extensions = extensionService.sortByDownloads(extensions);
//
//        for (int i = 0; i < extensions.size(); i++) {
//            Assert.assertEquals(downloads[6-i], extensions.get(i).getDownloadsCounter());
//        }
//    }
//
//    @Test
//    public void sortByName_shouldSortByNameInAlphabeticalOrder() {
//        List<String> names = Arrays.asList("Gosho", "Pesho", "Misho", "Anna", "Banana");
//        List<Extension> extensions = new ArrayList<>();
//        Helpers.fillListWithPublishedExtensions(extensions, 5);
//        for (int i = 0; i < extensions.size(); i++) {
//            extensions.get(i).setName(names.get(i));
//        }
//        Collections.sort(names);
//
//        extensions = extensionService.sortByName(extensions);
//
//        for (int i = 0; i < extensions.size(); i++) {
//            Assert.assertEquals(names.get(i), extensions.get(i).getName());
//        }
//    }
//
//    @Test
//    public void sortByLastCommit_shouldSortByLastCommitDateInReverseOrder() {
//        List<Date> commitDates = Arrays.asList(new Date(544765595L), new Date(1447655953L), new Date(1544765595L),
//                new Date(1437655953L), new Date(1448655953L), new Date(1534754595L), new Date(1484655953L), new Date(1684655953L));
//
//        List<Extension> extensions = new ArrayList<>();
//        Helpers.fillListWithPublishedExtensions(extensions, 8);
//        for (int i = 0; i < extensions.size(); i++) {
//            extensions.get(i).setLastCommitDate(commitDates.get(i));
//        }
//        commitDates.sort(Comparator.reverseOrder());
//
//        extensions = extensionService.sortByLastCommit(extensions);
//
//        for (int i = 0; i < extensions.size(); i++) {
//            Assert.assertEquals(commitDates.get(i), extensions.get(i).getLastCommitDate());
//        }
//    }
//
//    @Test
//    public void filterByName_shouldCallRepositoryMethod() {
//        String name = "test";
//        extensionService.filterPublishedByName(name);
//
//        verify(mockExtensionRepository).filterPublishedByName(name);
//    }
//
//    @Test
//    public void publishExtension_shouldSetExtensionPublishedDateToNow() {
//        Extension extension = new Extension();
//
//        extensionService.publishExtension(extension);
//
//        Assert.assertNotNull(extension.getPublishedDate());
//        Assert.assertTrue(new Date().getTime() - extension.getPublishedDate().getTime() < 1000);
//    }
//
//    @Test
//    public void publishExtension_shouldCallUpdateExtensionRepositoryMethod() {
//        Extension extension = new Extension();
//
//        extensionService.publishExtension(extension);
//
//        verify(mockExtensionRepository).update(extension);
//    }
//
//    public static class Helpers {
//        public static void fillListWithPublishedExtensions(List<Extension> list, int count) {
//            for (int i = 0; i < count; i++) {
//                Extension extension = new Extension();
//                extension.setPublishedDate(new Date());
//                list.add(extension);
//            }
//        }
//
//        public static void fillListWithUnpublishedExtensions(List<Extension> list, int count) {
//            for (int i = 0; i < count; i++) {
//                list.add(new Extension());
//            }
//        }
//    }
//}