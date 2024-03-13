package hu.csercsak_albert.banking_system.dto;

import hu.csercsak_albert.banking_system.enums.TransactionType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

    @Id
    private long id;

    private TransactionType transactionType;

    private long fromAccountNumber;

    private long toAccountNumber;

    @Min(1)
    private double amount;

    private LocalDateTime time;
}
