package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.RoleRepository;
import com.teammistique.extensionrepository.models.security.Role;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class RoleServiceImplTest {
    private RoleServiceImpl roleService;
    private RoleRepository mockRoleRepository = mock(RoleRepository.class);

    @Before
    public void setUp(){
        roleService = new RoleServiceImpl(mockRoleRepository);
    }

    @Test
    public void getRoleByName_shouldCallRepoMethod() {
        String roleName = "role";
        when(mockRoleRepository.getByName(roleName)).thenReturn(new Role(roleName));
        Role role = roleService.getRoleByName(roleName);
        verify(mockRoleRepository).getByName(roleName);
    }

    @Test
    public void getRoleByID_shouldCallRepoMethod() {
        int id = 5;
        when(mockRoleRepository.findById(id)).thenReturn(new Role());
        Role role = roleService.getRoleByID(id);
        verify(mockRoleRepository).findById(id);
    }
}