package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.entity.Account;
import hu.csercsak_albert.banking_system.entity.User;
import hu.csercsak_albert.banking_system.exceptions.AccountNotFoundException;
import hu.csercsak_albert.banking_system.mapper.UserMapper;
import hu.csercsak_albert.banking_system.repository.AccountRepository;
import hu.csercsak_albert.banking_system.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Provides account management services including creating and deleting accounts.
 * <p>
 * This service class implements the AccountService interface and provides methods
 * for creating new accounts and deleting existing accounts. It interacts with the
 * AccountRepository to perform database operations related to account management.
 * <p>
 * Account creation involves generating a new account number using the account number
 * generator service, initializing the account balance to zero, and associating the
 * account with the specified user. The newly created account is then saved to the
 * database.
 * <p>
 * Account deletion requires verifying the ownership of the account by checking if the
 * authenticated user is the owner. If the user is the owner, the account is deleted
 * from the database. Otherwise, a forbidden status code is returned.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private AccountNumberGeneratorService accountNumberGenerator;

    /**
     * Creates a new account for the specified user.
     * <p>
     * This method generates a new account number using the account number generator service,
     * initializes the account balance to zero, and associates the account with the provided user.
     * The newly created account is then saved to the database using the account repository.
     * <p>
     * If the account creation is successful, HttpStatus.CREATED is returned.
     *
     * @param ownerUser The user who will be the owner of the new account.
     * @return HttpStatus.CREATED if the account is successfully created.
     */
    @Override
    public HttpStatus createNewAccount(UserDto ownerUser) {
        User owner = UserMapper.mapToUser(ownerUser);
        Account account = Account.builder()
                .user(owner)
                .balance(0.0d)
                .accountNumber(accountNumberGenerator.generateAccountNumber())
                .build();
        accountRepository.save(account);
        return HttpStatus.CREATED;
    }

    /**
     * Deletes the account with the specified account number if the authenticated user is the owner.
     *
     * @param user          The authenticated user.
     * @param accountNumber The account number of the account to be deleted.
     * @return The HTTP status representing the success of the account deletion operation (HttpStatus.NO_CONTENT).
     * @throws AccountNotFoundException if no account is found with the specified account number.
     */
    @Override
    public HttpStatus deleteAccount(UserDto user, Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with that account number(%d)".formatted(accountNumber)));
        if (isUserOwner(account, user)) {
            accountRepository.delete(account);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.FORBIDDEN;
    }

    // Helper method
    private boolean isUserOwner(Account account, UserDto user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return account.getUser().getId() == user.getId() && user.getUsername().equals(username);
    }
}
