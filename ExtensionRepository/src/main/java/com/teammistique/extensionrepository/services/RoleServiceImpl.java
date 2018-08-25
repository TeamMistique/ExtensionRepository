package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.RoleRepository;
import com.teammistique.extensionrepository.models.security.Role;
import com.teammistique.extensionrepository.services.base.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String role) {
        return roleRepository.getByName(role);
    }

    @Override
    public Role getRoleByID(int id) {
        return roleRepository.findById(id);
    }
}
