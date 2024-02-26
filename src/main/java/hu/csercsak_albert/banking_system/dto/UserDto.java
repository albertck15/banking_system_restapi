package hu.csercsak_albert.banking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private Integer accountNumber;

    @NotBlank(message = "Username is required")
    @NotNull(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @NotNull(message = "Password is required")
    private String password;

    @NotBlank(message = "Email is required")
    @NotNull(message = "Email is required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "First name is required")
    @NotNull(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @NotNull(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Date of birth is required")
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

    private BalanceDto balanceDto;
}
