package org.example.springmicroserviceshandson.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.entities.User;
import org.example.springmicroserviceshandson.repositories.UserRepository;
import org.example.springmicroserviceshandson.services.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

}
