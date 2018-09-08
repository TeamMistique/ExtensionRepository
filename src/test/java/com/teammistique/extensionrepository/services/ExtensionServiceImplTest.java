package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.config.security.JwtTokenUtil;
import com.teammistique.extensionrepository.data.base.ExtensionRepository;
import com.teammistique.extensionrepository.models.DTO.ExtensionDTO;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.services.base.GitHubService;
import com.teammistique.extensionrepository.services.base.StorageService;
import com.teammistique.extensionrepository.services.base.TagService;
import com.teammistique.extensionrepository.services.base.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.acl.Owner;
import java.util.*;

import static org.mockito.Mockito.*;

public class ExtensionServiceImplTest {

    private GitHubService mockGitHubService = mock(GitHubService.class);
    private ExtensionRepository mockExtensionRepository = mock(ExtensionRepository.class);
    private StorageService mockFileService = mock(StorageService.class);
    private TagService mockTagService = mock(TagService.class);
    private UserService mockUserService = mock(UserService.class);
    private JwtTokenUtil mockJwtTokenUtil = mock(JwtTokenUtil.class);

    private ExtensionServiceImpl extensionService;

    @Before
    public void setUp() {
        extensionService = new ExtensionServiceImpl(mockGitHubService, mockExtensionRepository, mockFileService, mockTagService, mockUserService, mockJwtTokenUtil);
    }

    public static class Helpers {
        public static ExtensionDTO createFakeExtensionDto(){
            ExtensionDTO dto = new ExtensionDTO();
            dto.setName("name");
            dto.setDescription("description");
            dto.setLink("github");
            dto.setTagNames(Arrays.asList("Tag1", "Tag2", "Tag3"));
            dto.setImage("image");
            dto.setFile("file");
            return dto;
        }

        public static void fillListWithPublishedExtensions(List<Extension> list, int count) {
            for (int i = 0; i < count; i++) {
                Extension extension = new Extension();
                extension.setPublishedDate(new Date());
                list.add(extension);
            }
        }

        public static void fillListWithUnpublishedExtensions(List<Extension> list, int count) {
            for (int i = 0; i < count; i++) {
                list.add(new Extension());
            }
        }
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
    public void listById_shouldReturnWhenAdmin() {
        String authToken = "token";
        int id = 5;
        Extension extension = new Extension();

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(
                true
        );

        when(mockExtensionRepository.findById(id)).thenReturn(
                extension
        );
        Assert.assertEquals(extension, extensionService.getExtensionById(id, authToken));
    }

    @Test
    public void listById_shouldReturnWhenUserAndNotOwner() {
        String authToken = "token";
        int id = 5;
        User owner = new User();
        owner.setUsername("Radik");
        Extension extension = new Extension();
        extension.setOwner(owner);

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(
                false
        );

        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn(
                "username"
        );

        when(mockExtensionRepository.findById(id)).thenReturn(
                extension
        );
        Assert.assertEquals(null, extensionService.getExtensionById(id, authToken));
    }

    @Test
    public void listById_shouldReturnWhenUserAndOwner() {
        String authToken = "token";
        int id = 5;
        User owner = new User();
        owner.setUsername("Radik");
        Extension extension = new Extension();
        extension.setOwner(owner);

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(
                false
        );

        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn(
                "Radik"
        );

        when(mockExtensionRepository.findById(id)).thenReturn(
                extension
        );
        Assert.assertEquals(extension, extensionService.getExtensionById(id, authToken));
    }

    @Test
    public void listById_shouldReturnWhenNoToken() {
        String authToken = null;
        int id = 5;
        User owner = new User();
        owner.setUsername("Radik");
        Extension extension = new Extension();
        extension.setOwner(owner);

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(
                false
        );

        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn(
                "Radik"
        );

        when(mockExtensionRepository.findById(id)).thenReturn(
                extension
        );
        Assert.assertEquals(null, extensionService.getExtensionById(id, authToken));
    }

    @Test
    public void createNewExtension_shouldReturnNewExtension() {
        ExtensionDTO dto = Helpers.createFakeExtensionDto();
        String authToken = "token";

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(
                true
        );

        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn(
                "Radik"
        );
        Extension extension = extensionService.createExtension(dto,  authToken);
        verify(mockExtensionRepository).create(any());
    }




    @Test
    public void updateExtension_shouldChangeAttributesOfTheExtension() {
        Extension extension = new Extension();
        extension.setName("Test");
        extension.setFile("downloadFile/file.txt");
        extension.setImage("image");
        String authToken = "token";
        int id = 5;

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(
                true
        );

        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn(
                "Radik"
        );
        ExtensionDTO dto = Helpers.createFakeExtensionDto();
        dto.setName("DTO");
        dto.setFile("downloadFile/file.txt");

        dto.setId(id);
        when(mockExtensionRepository.findById(id)).thenReturn(extension);
        extensionService.updateExtension(dto, authToken );

        Assert.assertEquals(extension.getName(), "DTO");
        Assert.assertEquals(extension.getFile(),"downloadFile/file.txt" );

    }

    @Test
    public void deleteExtension_ShouldRemoveTheExtensionWhenOwner(){
        String authToken = null;
        int id = 5;
        User owner = new User();
        owner.setUsername("Radik");
        Extension extension = new Extension();
        extension.setOwner(owner);
        extension.setImage("downloadFile/image.png");
        extension.setFile("downloadFile/file.txt");

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(
                false
        );

        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn(
                "Radik"
        );

        when(mockExtensionRepository.findById(id)).thenReturn(
                extension
        );

//        when(mockFileService.deleteFile(any())).thenReturn(true);

        extensionService.deleteExtension(id, authToken );

        verify(mockExtensionRepository).delete(id);
    }


    @Test
    public void deleteExtension_ShouldRemoveTheExtensionWhenAdmin(){
        String authToken = null;
        int id = 5;
        User owner = new User();
        owner.setUsername("Radik");
        Extension extension = new Extension();
        extension.setOwner(owner);
        extension.setImage("downloadFile/image.png");
        extension.setFile("downloadFile/file");

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(
                true
        );

        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn(
                "someone"
        );

        when(mockExtensionRepository.findById(id)).thenReturn(
                extension
        );

        extensionService.deleteExtension(id, authToken );

        verify(mockExtensionRepository).delete(id);
    }

    @Test
    public void deleteExtension_ShouldRemoveTheExtensionWhenNotOwner(){
        String authToken = null;
        int id = 5;
        User owner = new User();
        owner.setUsername("Radik");
        Extension extension = new Extension();
        extension.setOwner(owner);

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(
                false
        );

        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn(
                "noone"
        );

        when(mockExtensionRepository.findById(id)).thenReturn(
                extension
        );

        extensionService.deleteExtension(id, authToken );

        verify(mockExtensionRepository, never()).delete(id);
    }

    @Test
    public void listFeaturedExtension_shouldCallRepositoryMethod() {
        extensionService.listFeaturedExtensions(true);
        verify(mockExtensionRepository).listFeaturedExtensions(true);

    }

    @Test
    public void listUnFeaturedExtension_shouldCallRepositoryMethod() {
        extensionService.listFeaturedExtensions(false);
        verify(mockExtensionRepository).listFeaturedExtensions(false);
    }

    @Test
    public void listPopularExtensions_shouldCallRepositoryMethod(){
        extensionService.listPopularExtensions();

        verify(mockExtensionRepository).listPopularExtensions(extensionService.getMaxListSize());
    }

    @Test
    public void listNewExtensions_shouldCallRepositoryMethod() {
        extensionService.listNewExtensions();

        verify(mockExtensionRepository).listNewExtensions(extensionService.getMaxListSize());
    }

    @Test
    public void listPublishedExtensions_shouldCallRepositoryMethod() {
        extensionService.listPublishedExtensions(true);
        verify(mockExtensionRepository).listPublishedExtensions(true);

        extensionService.listPublishedExtensions(false);
        verify(mockExtensionRepository).listPublishedExtensions(false);
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








}