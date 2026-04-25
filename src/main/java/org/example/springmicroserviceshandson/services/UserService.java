package org.example.springmicroserviceshandson.services;

import org.example.springmicroserviceshandson.domain.entities.User;


public interface UserService {
    User findByEmail(String email);
}
