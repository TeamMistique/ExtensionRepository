package com.teammistique.extensionrepository.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
    public String getUsernameFromToken(String authToken) {
        return null;
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        return false;
    }
}
