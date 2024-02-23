package hu.csercsak_albert.banking_system.dto;

import hu.csercsak_albert.banking_system.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private TransactionType transactionType;

    private int fromAccountId;

    private int toAccountId;

    private double amount;

    private LocalDateTime time;
}
