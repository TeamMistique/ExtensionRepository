package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.models.security.Role;

import java.util.List;

public interface UserService {
    User findOne(String username);
    List<User> findAll();
    User findById(Integer id);
    User save(User user, List<Role> roles);
    User changeEnabled(User user);
}
