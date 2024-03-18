package hu.csercsak_albert.banking_system.dto;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    @Id
    private Long accountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDto user;

    private Double balance;
}
