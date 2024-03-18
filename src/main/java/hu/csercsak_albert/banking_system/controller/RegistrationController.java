package hu.csercsak_albert.banking_system.controller;

import hu.csercsak_albert.banking_system.dto.RegistrationRequest;
import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Controller class for handling user registration operations.
 */
@RestController
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user.
     *
     * @param request The registration request containing user details.
     * @return ResponseEntity with a message indicating successful registration.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        LocalDate dob = LocalDate.parse(request.getDob().replaceAll("[. ]", "-"));
        UserDto userDto = UserDto.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateOfBirth(dob + "")
                .build();
        userService.createUser(userDto);
        return ResponseEntity.ok("Registration successful");
    }
}
