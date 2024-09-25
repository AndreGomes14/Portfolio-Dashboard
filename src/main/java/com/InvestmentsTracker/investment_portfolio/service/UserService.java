package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.model.User;
import com.InvestmentsTracker.investment_portfolio.model.Role;
import com.InvestmentsTracker.investment_portfolio.repository.UserRepository;
import com.InvestmentsTracker.investment_portfolio.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Method for registering a new user
    public void registerUser(User user) {
        // Check if the user already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Fetch the default role (e.g., "ROLE_USER")
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            // Optionally, create the role if it doesn't exist
            userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }

        // Assign the role to the user
        user.setRoles(Collections.singleton(userRole));

        // Encode the password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save the user
        userRepository.save(user);
    }

    // Implementing the loadUserByUsername method required by UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
