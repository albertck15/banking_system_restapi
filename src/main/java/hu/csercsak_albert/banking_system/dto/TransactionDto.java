package hu.csercsak_albert.banking_system.dto;

import hu.csercsak_albert.banking_system.enums.TransactionType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

    @Id
    private Long id;

    private TransactionType transactionType;

    @NotNull
    @NotBlank()
    private int fromAccountNumber;

    private int toAccountNumber;

    @Min(1)
    private double amount;

    private LocalDateTime time;
}
