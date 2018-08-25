package com.teammistique.extensionrepository.web;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.teammistique.extensionrepository.config.security.JwtTokenUtil;
import com.teammistique.extensionrepository.models.security.AuthToken;
import com.teammistique.extensionrepository.models.security.LoginUser;
import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.models.security.Role;
import com.teammistique.extensionrepository.services.base.RoleService;
import com.teammistique.extensionrepository.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService, RoleService roleService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/token")
    public ResponseEntity register(@ModelAttribute LoginUser loginUser) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping("/signup")
    public User saveUser(@ModelAttribute User user) {
        Role roleUser = roleService.getRoleByName("ROLE_USER");
        return userService.save(user, Collections.singletonList(roleUser));
    }

    @PostMapping("/adminsignup")
    @PreAuthorize("hasRole('ADMIN')")
    public User saveAdmin(@ModelAttribute User user){
        List<Role> roles = Arrays.asList(roleService.getRoleByName("ROLE_ADMIN"), roleService.getRoleByName("ROLE_USER"));
        return userService.save(user, roles);
    }
}
