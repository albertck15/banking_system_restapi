package hu.csercsak_albert.banking_system.controller;

import hu.csercsak_albert.banking_system.dto.AccountDto;
import hu.csercsak_albert.banking_system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller class for handling account-related operations.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Create a new account for the logged-in User
     *
     * @return HttpStatus.OK if the account creation is successful.
     */
    @GetMapping("/create")
    public HttpStatus createNewAccount() {
        return accountService.createNewAccount();
    }

    /**
     * Delete an account by account number.
     *
     * @param accountNumber The account number of the account to be deleted.
     * @return HttpStatus.OK if the account deletion is successful.
     * @throws AccessDeniedException when trying to delete an account that is not related to the logged-in user
     */
    @DeleteMapping("/delete/{accountNumber}")
    public HttpStatus deleteAccount(@PathVariable Long accountNumber, @AuthenticationPrincipal UserDetails userDetails) {
        AccountDto accountDto = accountService.getAccountByAccountNumber(accountNumber);
        if (accountDto.getUser().getUsername().equals(userDetails.getUsername())) {
            return accountService.deleteAccount(accountNumber);
        }
        throw new AccessDeniedException("You do not have the right permissions");
    }

    /**
     * Retrieve all accounts owned by the current user.
     *
     * @return A list of AccountDto objects representing the accounts owned by the current user.
     */
    @GetMapping("/get-my-accounts")
    public List<AccountDto> getMyAccounts() {
        return accountService.getAccounts();
    }

    /**
     * Retrieve an account by its account number.
     *
     * @param accountNumber The account number of the account to be retrieved.
     * @return An AccountDto object representing the account with the specified account number.
     */
    @GetMapping("/{accountNumber}")
    public AccountDto getAccountById(@PathVariable Long accountNumber, @AuthenticationPrincipal UserDetails userDetails) {
        //If the logged-in user is not the owner, sensitive fields are set to null. (balance, user)
        return accountService.getAccountByAccountNumber(accountNumber);
    }

    /**
     * Retrieve accounts owned by others.
     *
     * @return A map containing account owner names as keys and lists of account numbers as values.
     */
    @GetMapping("/get-all")
    public Map<String, List<Long>> getAccountsOwnedByOthers() {
        return accountService.getAccountsOwnedByOthers();
    }
}
