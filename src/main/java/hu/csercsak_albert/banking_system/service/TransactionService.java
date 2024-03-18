package hu.csercsak_albert.banking_system.service;

import hu.csercsak_albert.banking_system.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    TransactionDto deposit(TransactionDto transactionDto);

    TransactionDto withdraw(TransactionDto transactionDto);

    TransactionDto transfer(TransactionDto transactionDto);

    List<TransactionDto> getTransactions();

}
