package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.models.security.Role;

public interface RoleService {
    Role getRoleByName(String role);
    Role getRoleByID(int id);
}
