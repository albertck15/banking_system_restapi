package hu.csercsak_albert.banking_system.controller;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        userDto.setCreatedAtDate(LocalDateTime.now());
        userDto.setUpdatedAtDate(LocalDateTime.now());
        userDto.setLastLoginDate(LocalDateTime.now());
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getAccountById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
