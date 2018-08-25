package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public List listUsers(){
        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public User getOne(@PathVariable(value = "id") Integer id){
        return userService.findById(id);
    }
}
