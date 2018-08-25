package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.models.User;

import java.util.List;

public interface UserService {
    User findOne(String username);
    List<User> findAll();
    User findById(Integer id);
}
