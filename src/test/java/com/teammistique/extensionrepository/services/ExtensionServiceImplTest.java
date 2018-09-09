package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.config.security.JwtTokenUtil;
import com.teammistique.extensionrepository.data.base.ExtensionRepository;
import com.teammistique.extensionrepository.exceptions.MyFileNotFoundException;
import com.teammistique.extensionrepository.models.DTO.ExtensionDTO;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.services.base.GitHubService;
import com.teammistique.extensionrepository.services.base.StorageService;
import com.teammistique.extensionrepository.services.base.TagService;
import com.teammistique.extensionrepository.services.base.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    private static class Helpers {
        private static ExtensionDTO createFakeExtensionDto() {
            ExtensionDTO dto = new ExtensionDTO();
            dto.setName("name");
            dto.setDescription("description");
            dto.setLink("github");
            dto.setTagNames(Arrays.asList("Tag1", "Tag2", "Tag3"));
            dto.setImage("http://localhost:8080/api/files/downloadFile/meredith%20grey%2011x12.png");
            dto.setFile("http://localhost:8080/api/files/downloadFile/meredith%20grey%2011x12.png");
            return dto;
        }

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

    @Test
    public void listAll_shouldReturnAListOfExtensions() {
        when(mockExtensionRepository.listAll()).thenReturn(
                new ArrayList<>()
        );
        List<Extension> result = extensionService.listAllExtensions();
        Assert.assertEquals(new ArrayList<Extension>(), result);
    }

    @Test
    public void getExtensionById_shouldReturnExtension_whenAdmin() {
        String authToken = "token";
        int id = 5;
        Extension extension = new Extension();

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(true);
        when(mockExtensionRepository.findById(id)).thenReturn(extension);

        Assert.assertEquals(extension, extensionService.getExtensionById(id, authToken));
    }

    @Test
    public void getExtensionById_shouldReturnNull_whenUserAndNotOwner() {
        String authToken = "token";
        int id = 5;
        User owner = new User();
        owner.setUsername("Radik");
        Extension extension = new Extension();
        extension.setOwner(owner);

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(false);
        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn("username");
        when(mockExtensionRepository.findById(id)).thenReturn(extension);

        Assert.assertNull(extensionService.getExtensionById(id, authToken));
    }

    @Test
    public void getExtensionById_shouldReturnExtension_whenUserAndOwner() {
        String authToken = "token";
        int id = 5;
        User owner = new User();
        owner.setUsername("Radik");
        Extension extension = new Extension();
        extension.setOwner(owner);

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(false);
        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn("Radik");
        when(mockExtensionRepository.findById(id)).thenReturn(extension);

        Assert.assertEquals(extension, extensionService.getExtensionById(id, authToken));
    }

    @Test
    public void getExtensionById_shouldReturnExtension_whenPublishedAndNoToken() {
        int id = 5;
        Extension extension = new Extension();
        extension.setPublishedDate(new Date());
        when(mockExtensionRepository.findById(id)).thenReturn(extension);

        Assert.assertEquals(extension, extensionService.getExtensionById(id, null));
    }

    @Test
    public void getExtensionById_shouldReturnNull_whenUnpublishedAndNoToken() {
        int id = 5;
        Extension extension = new Extension();
        when(mockExtensionRepository.findById(id)).thenReturn(extension);

        Assert.assertNull(extensionService.getExtensionById(id, null));
    }

    @Test
    public void createExtension_shouldReturnNewUnpublishedExtension_whenNotAdmin() {
        ExtensionDTO dto = Helpers.createFakeExtensionDto();
        when(mockJwtTokenUtil.isAdmin(anyString())).thenReturn(false);
        when(mockExtensionRepository.create(any(Extension.class))).thenAnswer(extension -> extension.getArgument(0));

        Extension extension = extensionService.createExtension(dto, "");

        Assert.assertNull(extension.getPublishedDate());
    }

    @Test
    public void createExtension_shouldReturnPublishedExtension_whenAdmin() {
        ExtensionDTO dto = Helpers.createFakeExtensionDto();
        when(mockJwtTokenUtil.isAdmin(anyString())).thenReturn(true);
        when(mockExtensionRepository.create(any(Extension.class))).thenAnswer(extension -> extension.getArgument(0));

        Extension extension = extensionService.createExtension(dto, "");

        Assert.assertNotNull(extension.getPublishedDate());
    }

    @Test
    public void updateExtension_shouldReturnNull_whenNotOwner() {
        Extension extension = new Extension();
        User owner = new User();
        extension.setOwner(owner);
        when(mockExtensionRepository.findById(anyInt())).thenReturn(extension);
        when(mockJwtTokenUtil.isAdmin(anyString())).thenReturn(false);
        when(mockJwtTokenUtil.getUsernameFromToken(anyString())).thenReturn("");

        Extension result = extensionService.updateExtension(Helpers.createFakeExtensionDto(), "");

        Assert.assertNull(result);
    }

    @Test
    public void updateExtension_shouldSetPublishedDateToNull_whenOwnerButNotAdmin() {
        ExtensionDTO dto = Helpers.createFakeExtensionDto();
        Extension extension = new Extension();
        String username = "username";
        User owner = new User(username, "");
        extension.setOwner(owner);
        extension.setFile(dto.getFile());
        extension.setImage(dto.getImage());
        when(mockExtensionRepository.findById(anyInt())).thenReturn(extension);
        when(mockJwtTokenUtil.isAdmin(anyString())).thenReturn(false);
        when(mockJwtTokenUtil.getUsernameFromToken(anyString())).thenReturn(username);
        when(mockExtensionRepository.update(any(Extension.class))).thenAnswer(i -> i.getArgument(0));

        Extension result = extensionService.updateExtension(dto, "");

        Assert.assertNull(result.getPublishedDate());
    }

    @Test
    public void updateExtension_shouldUpdateExtensionInfoAndNotChangePublishedDate_whenAdmin() throws MyFileNotFoundException {
        ExtensionDTO dto = Helpers.createFakeExtensionDto();
        Extension extension = new Extension();
        extension.setFile("http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg");
        extension.setImage("http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg");
        extension.setVersion(0);
        when(mockExtensionRepository.findById(anyInt())).thenReturn(extension);
        when(mockJwtTokenUtil.isAdmin(anyString())).thenReturn(true);
        when(mockTagService.createTag(any(Tag.class))).thenAnswer(tag -> tag.getArgument(0));
        when(mockExtensionRepository.update(any(Extension.class))).thenAnswer(i -> i.getArgument(0));

        Extension result = extensionService.updateExtension(dto, "");

        List<String> dtoTags = dto.getTagNames();
        List<Tag> tags = result.getTags();

        Assert.assertEquals(dtoTags.size(), tags.size());
        for (int i = 0; i < dtoTags.size(); i++) {
            Assert.assertEquals(dtoTags.get(i), tags.get(i).getTagName());
        }

        Assert.assertEquals(extension.getPublishedDate(), result.getPublishedDate());
        Assert.assertEquals(dto.getFile(), result.getFile());
        Assert.assertEquals(dto.getImage(), result.getImage());
        Assert.assertEquals(dto.getName(), result.getName());
        Assert.assertEquals(dto.getDescription(), result.getDescription());
        Assert.assertEquals(dto.getLink(), result.getLink());
        verify(mockFileService, times(2)).deleteFile(anyString());
    }

    @Test
    public void updateExtension_shouldStillSaveNewFile_evenWhenOldOneWasNotFound() throws MyFileNotFoundException {
        ExtensionDTO dto = Helpers.createFakeExtensionDto();
        Extension extension = new Extension();
        extension.setFile("http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg");
        extension.setImage("http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg");
        extension.setVersion(0);
        when(mockExtensionRepository.findById(anyInt())).thenReturn(extension);
        when(mockJwtTokenUtil.isAdmin(anyString())).thenReturn(true);
        when(mockTagService.createTag(any(Tag.class))).thenAnswer(tag -> tag.getArgument(0));
        when(mockExtensionRepository.update(any(Extension.class))).thenAnswer(i -> i.getArgument(0));
        doThrow(MyFileNotFoundException.class).when(mockFileService).deleteFile(anyString());

        Extension result = extensionService.updateExtension(dto, "");

        Assert.assertEquals(dto.getFile(), result.getFile());
        Assert.assertEquals(dto.getImage(), result.getImage());
    }

    @Test
    public void deleteExtension_ShouldRemoveTheExtension_whenOwner() throws MyFileNotFoundException {
        int id = 1;
        String authToken = "";
        String username = "Radik";
        User owner = new User();
        owner.setUsername(username);
        Extension extension = new Extension();
        extension.setOwner(owner);
        extension.setImage("downloadFile/image.png");
        extension.setFile("downloadFile/file.txt");

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(false);
        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn(username);
        when(mockExtensionRepository.findById(id)).thenReturn(extension);

        extensionService.deleteExtension(id, authToken);

        verify(mockFileService, times(2)).deleteFile(anyString());
        verify(mockExtensionRepository).delete(id);
    }

    @Test
    public void deleteExtension_shouldStillDeleteExtension_evenWhenFileAndImageWereNotFound() throws MyFileNotFoundException {
        int id = 1;
        String authToken = "";
        Extension extension = new Extension();
        extension.setImage("downloadFile/image.png");
        extension.setFile("downloadFile/file.txt");

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(true);
        when(mockExtensionRepository.findById(id)).thenReturn(extension);
        doThrow(MyFileNotFoundException.class).when(mockFileService).deleteFile(anyString());

        extensionService.deleteExtension(id, authToken);

        verify(mockFileService, times(2)).deleteFile(anyString());
        verify(mockExtensionRepository).delete(id);
    }

    @Test
    public void deleteExtension_ShouldNotDeleteTheExtension_whenNotOwnerAndNotAdmin() {
        String authToken = "";
        int id = 5;
        String username = "Radik";
        User owner = new User();
        owner.setUsername(username);
        Extension extension = new Extension();
        extension.setOwner(owner);

        when(mockJwtTokenUtil.isAdmin(authToken)).thenReturn(false);
        when(mockJwtTokenUtil.getUsernameFromToken(authToken)).thenReturn("noone");
        when(mockExtensionRepository.findById(id)).thenReturn(extension);

        extensionService.deleteExtension(id, authToken);

        verify(mockExtensionRepository, never()).delete(id);
    }

    @Test
    public void listFeaturedExtension_shouldReturnRepositoryMethodResult() {
        boolean featured = true;
        List<Extension> extensions = Arrays.asList(
                new Extension(),
                new Extension(),
                new Extension()
        );
        when(mockExtensionRepository.listFeaturedExtensions(featured)).thenReturn(extensions);
        Assert.assertSame(extensions, extensionService.listFeaturedExtensions(featured));
    }

    @Test
    public void listPopularExtensions_shouldReturnRepoMethodResult() {
        int maxSize = extensionService.getMaxListSize();
        List<Extension> extensions = Arrays.asList(
                new Extension(),
                new Extension(),
                new Extension()
        );
        when(mockExtensionRepository.listPopularExtensions(maxSize)).thenReturn(extensions);
        Assert.assertSame(extensions, extensionService.listPopularExtensions());
    }

    @Test
    public void listPublishedExtensions_shouldReturnRepoMethodResult() {
        boolean published = false;
        List<Extension> extensions = Arrays.asList(
                new Extension(),
                new Extension(),
                new Extension()
        );
        when(mockExtensionRepository.listPublishedExtensions(published)).thenReturn(extensions);
        Assert.assertSame(extensions, extensionService.listPublishedExtensions(published));
    }

    @Test
    public void sortByPublishedDate_shouldSortByPublishedDateInReverseOrder() {
        List<Date> dates = Arrays.asList(new Date(1534692315464L), new Date(1341123762001L), new Date(1399129992001L));
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
            Assert.assertEquals(downloads[6 - i], extensions.get(i).getDownloadsCounter());
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

    @Test
    public void filterByName_shouldReturnRepoMethodResult() {
        String name = "test";
        List<Extension> extensions = Arrays.asList(
                new Extension(),
                new Extension(),
                new Extension()
        );
        when(mockExtensionRepository.filterPublishedByName(name)).thenReturn(extensions);
        Assert.assertSame(extensions, extensionService.filterPublishedByName(name));
    }
}


