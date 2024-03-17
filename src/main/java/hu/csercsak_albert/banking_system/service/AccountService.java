package hu.csercsak_albert.banking_system.service;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.entity.User;
import org.springframework.http.HttpStatus;

public interface AccountService {

    HttpStatus createNewAccount(UserDto ownerUser);

    HttpStatus deleteAccount(UserDto ownerUser, Long accountNumber);

}
