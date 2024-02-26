package hu.csercsak_albert.banking_system.controller;

import hu.csercsak_albert.banking_system.dto.TransactionDto;
import hu.csercsak_albert.banking_system.entity.Transaction;
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
        HttpStatus status = transactionService.deposit(transactionDto);
        transactionDto = transactionService
        return ResponseEntity.status().body();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TransactionDto>> listTransactionsById(@PathVariable Long id) {
        List<TransactionDto> transactions = transactionService.getTransactionsById(id);
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("There are no transactions with sender or receiver ID(%d)".formatted(id));
        }
        return
    }
}
