package hu.csercsak_albert.banking_system.dto;

import hu.csercsak_albert.banking_system.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @Id
    private Long id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    @Pattern(regexp = "\\d{4}(.| |,)\\d{1,2}(.| |,)\\d{1,2}", message = "Invalid date of birth")
    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
