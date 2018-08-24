package com.teammistique.extensionrepository.data.base;

import com.teammistique.extensionrepository.models.User;

public interface UserRepository extends GenericRepository<User> {
    User getUserByUsername(String username);
}
