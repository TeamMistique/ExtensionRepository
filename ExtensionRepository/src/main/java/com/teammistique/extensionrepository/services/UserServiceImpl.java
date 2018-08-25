package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.UserRepository;
import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findOne(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.listAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id);
    }


}
