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
    public void listFeaturedExtension_shouldCallRepositoryMethod() {
        extensionService.listFeaturedExtensions(true);
        verify(mockExtensionRepository).listFeaturedExtensions(true);

        extensionService.listFeaturedExtensions(false);
        verify(mockExtensionRepository).listFeaturedExtensions(false);
    }








}