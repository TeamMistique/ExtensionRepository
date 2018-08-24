package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.models.User;

public interface UserService {
    User getUserByUserName(String username);
}
