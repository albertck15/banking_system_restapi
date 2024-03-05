package hu.csercsak_albert.banking_system.controller;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getAccountById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto,
                                           @RequestParam(value = "initialAmount", required = false) Double initialAmount
    ) {
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setUpdatedAt(LocalDateTime.now());
        userDto.setLastLogin(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto, initialAmount));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) {
        UserDto updatedUserDto = userService.updateUser(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable Long id) {
        HttpStatus responseStatus = userService.deleteUser(id);
        return ResponseEntity.status(responseStatus).body("User successfully deleted.");
    }
}
