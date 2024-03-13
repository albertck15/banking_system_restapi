package hu.csercsak_albert.banking_system.service;

import hu.csercsak_albert.banking_system.entity.User;
import org.springframework.http.HttpStatus;

public interface AccountService {

    HttpStatus createNewAccount(User ownerUser);

    HttpStatus deleteAccount(User ownerUser, Long accountNumber);

}
