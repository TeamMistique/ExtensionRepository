package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.UserRepository;
import com.teammistique.extensionrepository.models.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private UserServiceImpl userService;
    private UserRepository mockUserRepository = mock(UserRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Before
    public void setUp(){
        userService = new UserServiceImpl(mockUserRepository, bCryptPasswordEncoder);
    }

    @Test
    public void findOne_shouldCallRepoMethod() {
        String username = "username";
        when(mockUserRepository.getUserByUsername(username)).thenReturn(new User());
        User user = userService.findOne(username);
        verify(mockUserRepository).getUserByUsername(username);
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void save() {
    }

    @Test
    public void changeEnabled() {
    }

    @Test
    public void loadUserByUsername() {
    }
}