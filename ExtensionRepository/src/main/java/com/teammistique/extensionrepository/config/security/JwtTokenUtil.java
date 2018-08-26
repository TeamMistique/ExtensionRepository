package com.teammistique.extensionrepository.config.security;

import com.teammistique.extensionrepository.models.User;

import com.teammistique.extensionrepository.models.security.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.teammistique.extensionrepository.models.security.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.teammistique.extensionrepository.models.security.Constants.SIGNING_KEY;

@Component
public class JwtTokenUtil implements Serializable {
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token){
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user){
        return doGenerateToken(user.getUsername(), user.getAuthorities());
    }

    //TODO generate tokens with other authorities
    private String doGenerateToken(String subject, Collection<? extends GrantedAuthority> roles){
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("TeamMystique")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
