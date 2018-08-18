package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.models.Status;
import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.mockito.Mockito.*;


public class ExtensionServiceImplTest {
    @Mock
    GenericRepository<Extension> mockExtensionRepository;

    ExtensionServiceImpl extensionService = new ExtensionServiceImpl(mockExtensionRepository);

    @Before
    public void beforeEach(){

    }

    @Test
    public void createExtension() {

    }


}