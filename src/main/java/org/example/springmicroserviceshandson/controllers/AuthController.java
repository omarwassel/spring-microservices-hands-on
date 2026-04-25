package org.example.springmicroserviceshandson.controllers;

import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.dtos.auth.AuthResponse;
import org.example.springmicroserviceshandson.domain.dtos.auth.LoginRequest;
import org.example.springmicroserviceshandson.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest loginRequest) {
        UserDetails user = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = authenticationService.generateToken(user);
        AuthResponse authResponse = AuthResponse
                .builder()
                .token(token)
                .expiresIn(86400L)
                .build();
        return ResponseEntity.ok(authResponse);
    }

}
