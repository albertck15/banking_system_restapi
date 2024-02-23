package hu.csercsak_albert.banking_system.entity;

import hu.csercsak_albert.banking_system.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private TransactionType transactionType; // DEPOSIT, WITHDRAWAL, TRANSFER

    private int fromAccountId;

    private int toAccountId;

    private double amount;

    @Column(nullable = false)
    private LocalDateTime time;
}
