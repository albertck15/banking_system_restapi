package hu.csercsak_albert.banking_system.mapper;

import hu.csercsak_albert.banking_system.dto.TransactionDto;
import hu.csercsak_albert.banking_system.entity.Transaction;

public class TransactionMapper {

    public static Transaction mapToTransaction(TransactionDto transactionDto) {
        return Transaction.builder()
                .id(transactionDto.getId())
                .transactionType(transactionDto.getTransactionType())
                .fromAccountNumber(transactionDto.getFromAccountNumber())
                .toAccountNumber(transactionDto.getFromAccountNumber())
                .amount(transactionDto.getAmount())
                .time(transactionDto.getTime())
                .build();
    }

    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType())
                .fromAccountNumber(transaction.getFromAccountNumber())
                .toAccountNumber(transaction.getToAccountNumber())
                .amount(transaction.getAmount())
                .time(transaction.getTime())
                .build();
    }
}
