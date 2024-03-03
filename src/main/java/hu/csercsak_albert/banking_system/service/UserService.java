package hu.csercsak_albert.banking_system.service;

import hu.csercsak_albert.banking_system.dto.UserDto;
import org.springframework.http.HttpStatus;

public interface UserService {

    UserDto createUser(UserDto userDto, Double initialAmount);

    UserDto updateUser(Long id, UserDto userDto);

    HttpStatus deleteUser(Long id);

    UserDto findByAccountNumber(int accountNumber);

    UserDto findById(Long id);

    boolean isExistByAccountNumber(int accountNumber);
}
