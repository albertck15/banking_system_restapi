package hu.csercsak_albert.banking_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "accounts")
public class Account {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long userId;
    private Long accountNumber;
    private Double balance;
}
