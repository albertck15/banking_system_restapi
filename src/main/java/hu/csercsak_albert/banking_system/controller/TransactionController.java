package hu.csercsak_albert.banking_system.controller;

import hu.csercsak_albert.banking_system.dto.TransactionDto;
import hu.csercsak_albert.banking_system.enums.TransactionType;
import hu.csercsak_albert.banking_system.exceptions.TransactionNotFoundException;
import hu.csercsak_albert.banking_system.service.AccountOwnerCheckerService;
import hu.csercsak_albert.banking_system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller class for handling transaction-related operations.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountOwnerCheckerService accountOwnerCheckerService;

    /**
     * Deposit funds into an account.
     *
     * @param transactionDto The transaction details.
     * @return ResponseEntity containing the deposited transaction details.
     */
    @PostMapping("/deposit")
    public ResponseEntity<TransactionDto> deposit(@RequestBody TransactionDto transactionDto) {
        transactionDto.setTime(LocalDateTime.now());
        transactionDto.setTransactionType(TransactionType.DEPOSIT);
        transactionDto = transactionService.deposit(transactionDto);
        return ResponseEntity.status(HttpStatus.OK).body(transactionDto);
    }

    /**
     * Withdraw funds from an account.
     *
     * @param transactionDto The transaction details.
     * @return ResponseEntity containing the withdrawn transaction details.
     */
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDto> withdraw(@RequestBody TransactionDto transactionDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (accountOwnerCheckerService.check(transactionDto.getFromAccountNumber(), userDetails.getUsername())) {
            transactionDto.setTime(LocalDateTime.now());
            transactionDto.setTransactionType(TransactionType.WITHDRAWAL);
            transactionDto = transactionService.withdraw(transactionDto);
            return ResponseEntity.status(HttpStatus.OK).body(transactionDto);
        }
        throw new AccessDeniedException("You do not have the right permissions");
    }

    /**
     * Transfer funds between accounts.
     *
     * @param transactionDto The transaction details.
     * @return ResponseEntity containing the transferred transaction details.
     */
    @PostMapping("/transfer")
    public ResponseEntity<TransactionDto> transfer(@RequestBody TransactionDto transactionDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (accountOwnerCheckerService.check(transactionDto.getFromAccountNumber(), userDetails.getUsername())) {
            transactionDto.setTime(LocalDateTime.now());
            transactionDto.setTransactionType(TransactionType.TRANSFER);
            transactionDto = transactionService.transfer(transactionDto);
            return ResponseEntity.status(HttpStatus.OK).body(transactionDto);
        }
        throw new AccessDeniedException("You do not have the right permissions");
    }

    /**
     * Retrieve transactions by account number.
     *
     * @return ResponseEntity containing a list of transactions associated with the account number.
     * @throws TransactionNotFoundException if there are no transactions found for the specified account number.
     */
    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> listTransactionsById() {
        List<TransactionDto> transactions = transactionService.getTransactions(); // returns transactions for the logged-in user
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("There are no transactions");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

}
