package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.entity.Account;
import hu.csercsak_albert.banking_system.exceptions.AccountNotFoundException;
import hu.csercsak_albert.banking_system.repository.AccountRepository;
import hu.csercsak_albert.banking_system.service.AccountOwnerCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountOwnerCheckerServiceImpl implements AccountOwnerCheckerService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean check(Long accountNumber, String username) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with the number(%d)".formatted(accountNumber)));

        return account.getUser().getUsername().equals(username);
    }
}
