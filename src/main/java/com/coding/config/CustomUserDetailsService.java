package com.coding.config;

import com.coding.dto.UserInfo;
import com.coding.exception.AuthorizationException;
import com.coding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // Method to load user by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user information from the database
        UserInfo userInfo = userRepository.findByUsername(username);
        if (userInfo == null) {
            // If user is not found, throw AuthorizationException
            throw new AuthorizationException("AUTHZ_FAILED", "Access Denied", HttpStatus.UNAUTHORIZED);
        }
        // Build and return UserDetails object using retrieved user information
        return org.springframework.security.core.userdetails.User.builder()
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .roles(userInfo.getRole())
                .build();
    }
}
