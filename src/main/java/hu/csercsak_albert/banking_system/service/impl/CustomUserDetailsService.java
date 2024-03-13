package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.entity.User;
import hu.csercsak_albert.banking_system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service class implementing Spring Security's UserDetailsService interface
 * to load user details by username and authenticate users.
 * <p>
 * This service class interacts with the UserRepository to retrieve user details
 * by username and verify user authentication by comparing passwords using a
 * PasswordEncoder. It implements the loadUserByUsername method to load user
 * details for authentication purposes.
 */
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return passwordEncoder.matches(password, user.getPassword());
    }
}
