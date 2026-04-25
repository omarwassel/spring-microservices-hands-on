package org.example.springmicroserviceshandson.security;

import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.entities.User;
import org.example.springmicroserviceshandson.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("username not found for:"+email));
        return new BlogUserDetails(user);
    }
}
