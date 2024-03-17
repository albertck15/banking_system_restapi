package hu.csercsak_albert.banking_system.service;

import hu.csercsak_albert.banking_system.dto.UserDto;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public interface UserService {

    HttpStatus createUser(UserDto userDto);

    HttpStatus updateUser(Long id, UserDto userDto);

    HttpStatus deleteUser(Long id);

    UserDto findById(Long id);

    UserDto findByUsername(String username);
}
