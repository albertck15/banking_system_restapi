package hu.csercsak_albert.banking_system.service;

import hu.csercsak_albert.banking_system.dto.AccountDto;
import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface AccountService {

    HttpStatus createNewAccount();

    HttpStatus deleteAccount(Long accountNumber);

    List<AccountDto> getAccounts();

    AccountDto getAccountByAccountNumber(Long AccountNumber);

    Map<String, List<Long>> getAccountsOwnedByOthers();
}
