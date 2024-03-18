package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.dto.AccountDto;
import hu.csercsak_albert.banking_system.entity.Account;
import hu.csercsak_albert.banking_system.entity.User;
import hu.csercsak_albert.banking_system.exceptions.AccountNotFoundException;
import hu.csercsak_albert.banking_system.exceptions.UserNotFoundException;
import hu.csercsak_albert.banking_system.mapper.AccountMapper;
import hu.csercsak_albert.banking_system.repository.AccountRepository;
import hu.csercsak_albert.banking_system.repository.UserRepository;
import hu.csercsak_albert.banking_system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountNumberGeneratorService accountNumberGenerator;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new account for the specified user.
     * <p>
     * This method generates a new account number using the account number generator service,
     * initializes the account balance to zero, and associates the account with the provided user.
     * The newly created account is then saved to the database using the account repository.
     * <p>
     * If the account creation is successful, HttpStatus.CREATED is returned.
     *
     * @return HttpStatus.CREATED if the account is successfully created.
     */
    @Override
    @Transactional
    public HttpStatus createNewAccount() {
        User owner = getLoggedInUser();
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
     * @param accountNumber The account number of the account to be deleted.
     * @return The HTTP status representing the success of the account deletion operation (HttpStatus.NO_CONTENT).
     * @throws AccountNotFoundException if no account is found with the specified account number.
     */
    @Override
    @Transactional
    public HttpStatus deleteAccount(Long accountNumber) {
        User user = getLoggedInUser();

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with that account number(%d)".formatted(accountNumber)));

        accountRepository.delete(account);

        return HttpStatus.NO_CONTENT;
    }

    @Override
    public List<AccountDto> getAccounts() {
        String username = getLoggedInUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with the username(%s)".formatted(username)));

        List<Account> accounts = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found for the user(%s)".formatted(username)));

        return accounts.stream().map(AccountMapper::mapToAccountDto).toList();
    }

    @Override
    public AccountDto getAccountByAccountNumber(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with that number(%d)".formatted(accountNumber)));

        if (!isUserOwner(account, getLoggedInUser())) {
            return AccountMapper.mapToAccountDtoNotOwned(account);
        }

        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public Map<String, List<Long>> getAccountsOwnedByOthers() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .collect(Collectors.groupingBy(
                        account -> account.getUser().getFirstName() + " " + account.getUser().getLastName(),
                        Collectors.mapping(Account::getAccountNumber, Collectors.toList())
                ));
    }

    // Helper methods

    private boolean isUserOwner(Account account, User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return account.getUser().getId() == user.getId() && user.getUsername().equals(username);
    }

    private String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private User getLoggedInUser() {
        return userRepository.findByUsername(getLoggedInUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with the username(%s)".formatted(getLoggedInUsername())));
    }
}
