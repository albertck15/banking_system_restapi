package hu.csercsak_albert.banking_system.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private Integer accountNumber;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private LocalDateTime createdAtDate;

    private LocalDateTime updatedAtDate;

    private LocalDateTime lastLoginDate;
}
