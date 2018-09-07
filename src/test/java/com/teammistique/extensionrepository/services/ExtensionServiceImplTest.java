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
}