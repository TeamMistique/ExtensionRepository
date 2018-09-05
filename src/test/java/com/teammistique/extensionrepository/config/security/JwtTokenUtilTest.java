package com.teammistique.extensionrepository.config.security;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class JwtTokenUtilTest {
    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6W3siaWQiOjEsInJvbGUiOiJST0xFX0FETUlOIiwiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9LHsiaWQiOjIsInJvbGUiOiJST0xFX1VTRVIiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlzcyI6IlRlYW1NeXN0aXF1ZSIsImlhdCI6MTUzNTUyMTI1OSwiZXhwIjoxNTM1NTM5MjU5fQ.ANqgV3vy6_Ukh4iXzV5R92RqgIztP-29DpmET3jjD9s";
    private static final HashMap<Object, Object> answers;
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    static{
        answers = new HashMap<>();
        answers.put("sub", "admin");
        answers.put("scopes", Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
        answers.put("iss", "TeamMystique");
        answers.put("iat", new Date(1535521259));
        answers.put("exp", new Date(1535539259));
    }

    @Test
    public void isAdmin_shouldReturnTrue_whenTheTokenBelongsToAnAdmin() {
        assertTrue(jwtTokenUtil.isAdmin(token));
    }
}