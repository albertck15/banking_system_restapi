package hu.csercsak_albert.banking_system.controller;

import hu.csercsak_albert.banking_system.dto.TransactionDto;
import hu.csercsak_albert.banking_system.entity.Transaction;
import hu.csercsak_albert.banking_system.enums.TransactionType;
import hu.csercsak_albert.banking_system.exceptions.TransactionNotFoundException;
import hu.csercsak_albert.banking_system.service.TransactionService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDto> deposit(@RequestBody TransactionDto transactionDto) {
        transactionDto.setTime(LocalDateTime.now());
        transactionDto.setTransactionType(TransactionType.DEPOSIT);
        transactionDto = transactionService.deposit(transactionDto);
        return ResponseEntity.status(HttpStatus.OK).body(transactionDto);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDto> withdraw(@RequestBody TransactionDto transactionDto) {
        transactionDto.setTime(LocalDateTime.now());
        transactionDto.setTransactionType(TransactionType.WITHDRAWAL);
        transactionDto = transactionService.withdraw(transactionDto);
        return ResponseEntity.status(HttpStatus.OK).body(transactionDto);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDto> transfer(@RequestBody TransactionDto transactionDto) {
        transactionDto.setTime(LocalDateTime.now());
        transactionDto.setTransactionType(TransactionType.TRANSFER);
        transactionDto = transactionService.transfer(transactionDto);
        return ResponseEntity.status(HttpStatus.OK).body(transactionDto);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionDto>> listTransactionsById(@PathVariable Long accountNumber) {
        List<TransactionDto> transactions = transactionService.getTransactionsByAccountNumber(accountNumber);
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("There are no transactions with this account number(%d)".formatted(accountNumber));
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
}
