package com.teammistique.extensionrepository.data.base;

import com.teammistique.extensionrepository.models.security.Role;

public interface RoleRepository extends NumericId<Role> {
    Role getByName(String role);
}
