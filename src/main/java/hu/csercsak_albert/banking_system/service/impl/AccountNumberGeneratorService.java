package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Service class responsible for generating unique account numbers.
 * <p>
 * This service class generates unique account numbers within a specified range
 * defined by the minimum and maximum values configured in the application
 * properties. It ensures uniqueness by checking if the generated account number
 * already exists in the account repository.
 * <p>
 * The generated account number is randomly generated within the configured range
 * and checked against existing account numbers in the repository. If a generated
 * account number already exists, a new one is generated until a unique account
 * number is found.
 */
@Service
public class AccountNumberGeneratorService {

    @Autowired
    private AccountRepository accountRepository;

    @Value("${account.number.min.value}")
    private int accNumberMin;

    @Value("${account.number.max.value}")
    private int accNumberMax;

    Long generateAccountNumber() {
        Random random = new Random();
        long accNumber = -1;
        do {
            accNumber = random.nextInt(accNumberMax - accNumberMin + 1) + accNumberMin;
        } while (accountRepository.existsByAccountNumber(accNumber));
        return accNumber;
    }
}
