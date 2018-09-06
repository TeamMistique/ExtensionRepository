package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.UserRepository;
import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.models.security.Role;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void findOne_shouldReturnRepoMethodResult() {
        String username = "username";
        User expected = new User();
        when(mockUserRepository.getUserByUsername(username)).thenReturn(expected);
        User user = userService.findOne(username);
        assertSame(expected, user);
    }

    @Test
    public void findAll_shouldReturnRepoMethodResult() {
        List<User> expected = Arrays.asList(
                new User(),
                new User(),
                new User()
        );
        when(mockUserRepository.listAll()).thenReturn(expected);
        List<User> users = userService.findAll();
        assertSame(expected, users);
    }

    @Test
    public void findById_shouldReturnRepoMethodResult() {
        Integer id = 12;
        User expected = new User();
        when(mockUserRepository.findById(id)).thenReturn(expected);
        User user = userService.findById(id);
        assertSame(expected, user);
    }

    @Test
    public void save_shouldReturnANewUserWithBCryptedPassword() {
        String rawPassword = "password";
        User userToCreate = new User("username", rawPassword);
        when(mockUserRepository.create(userToCreate)).thenReturn(userToCreate);

        User toReturn = userService.save(userToCreate, new ArrayList<>());

        assertTrue(bCryptPasswordEncoder.matches(rawPassword, toReturn.getPassword()));
    }

    @Test
    public void save_shouldReturnAnEnabledUser() {
        User toCreate = new User("username", "password");
        when(mockUserRepository.create(toCreate)).thenReturn(toCreate);
        User toReturn = userService.save(toCreate, new ArrayList<>());
        assertTrue(toReturn.isEnabled());
    }

    @Test
    public void save_shouldReturnAUserWithAListOfRoles() {
        List<Role> roles = Arrays.asList(
                new Role(),
                new Role()
        );
        User toCreate = new User("username", "password");
        when(mockUserRepository.create(toCreate)).thenReturn(toCreate);
        User toReturn = userService.save(toCreate, roles);
        assertSame(roles, toReturn.getAuthorities());
    }

    @Test
    public void changeEnabled_shouldEnableUser_ifItWasDisabled() {
        User toEnable = new User();
        toEnable.setEnabled(0);
        when(mockUserRepository.update(toEnable)).thenReturn(toEnable);
        User returned = userService.changeEnabled(toEnable);
        assertTrue(returned.isEnabled());
    }

    @Test
    public void changeEnabled_shouldDisableUser_ifItWasEnabled() {
        User toDisable = new User();
        toDisable.setEnabled(1);
        when(mockUserRepository.update(toDisable)).thenReturn(toDisable);
        User returned = userService.changeEnabled(toDisable);
        assertFalse(returned.isEnabled());
    }

    @Test
    public void loadUserByUsername_shouldCallFindOne() {
        String toFind = "toFind";
        UserDetails user = userService.loadUserByUsername(toFind);
        verify(mockUserRepository).getUserByUsername(toFind);
    }
}