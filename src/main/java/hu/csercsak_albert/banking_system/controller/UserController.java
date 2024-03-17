package hu.csercsak_albert.banking_system.controller;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.enums.Role;
import hu.csercsak_albert.banking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getAccountById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        UserDto userDto = userService.findById(id);
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()))) {
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } else if (userDetails.getUsername().equals(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
        throw new AccessDeniedException("You do not have the right permissions");
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) {
        HttpStatus status = userService.updateUser(id, userDto);
        userDto = userService.findById(id);
        return ResponseEntity.status(status).body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        HttpStatus responseStatus = userService.deleteUser(id);
        return ResponseEntity.status(responseStatus).body("User successfully deleted.");
    }
}
