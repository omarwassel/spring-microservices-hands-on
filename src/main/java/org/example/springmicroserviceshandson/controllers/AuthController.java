package org.example.springmicroserviceshandson.controllers;

import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.dtos.AuthResponse;
import org.example.springmicroserviceshandson.domain.dtos.LoginRequest;
import org.example.springmicroserviceshandson.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> createCategory(
            @RequestBody LoginRequest loginRequest) {
        UserDetails user = authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        String token = authenticationService.generateToken(user);
        AuthResponse authResponse = AuthResponse
                .builder()
                .token(token)
                .expiresIn(86400L)
                .build();
        return ResponseEntity.ok(authResponse);
    }

}
