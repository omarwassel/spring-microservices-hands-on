package org.example.springmicroserviceshandson.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Value("${jwt_secret_key}") private String secretKey;

    @Value("${jwt_expiry_ms}") private Long jwtExpiryMs;

    @Override
    public UserDetails authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .addClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public UserDetails validateToken(String token) {
        String userName = extractUserName(token);
        return userDetailsService.loadUserByUsername(userName);
    }

    private String extractUserName(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    private Key getSigningKey(){
        byte[] key = secretKey.getBytes();
        return Keys.hmacShaKeyFor(key);
    }

}
