package hu.csercsak_albert.banking_system.security;

import hu.csercsak_albert.banking_system.entity.User;
import hu.csercsak_albert.banking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class implementing Spring Security's UserDetailsService interface
 * to load user details by username and authenticate users.
 * <p>
 * This service class interacts with the UserRepository to retrieve user details
 * by username and verify user authentication by comparing passwords using a
 * PasswordEncoder. It implements the loadUserByUsername method to load user
 * details for authentication purposes.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

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
}
