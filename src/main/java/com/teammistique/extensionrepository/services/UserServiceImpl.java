package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.UserRepository;
import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.models.security.Role;
import com.teammistique.extensionrepository.services.base.RoleService;
import com.teammistique.extensionrepository.services.base.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    @Override
    public User save(User user, List<Role> roles) {
        String pass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        user.setEnabled(1);
        user.setRoles(roles);
        return userRepository.create(user);
    }

    @Override
    public User changeEnabled(User user) {
        user.setEnabled((user.isEnabled()) ? 0 : 1);
        return userRepository.update(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findOne(username);
    }
}
