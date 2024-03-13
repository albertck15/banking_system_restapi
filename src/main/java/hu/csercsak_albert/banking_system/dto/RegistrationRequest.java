package hu.csercsak_albert.banking_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotBlank(message = "Missing username")
    private String username;

    @NotBlank(message = "Missing first name")
    private String firstName;

    @NotBlank(message = "Missing last name")
    private String lastName;

    @NotBlank(message = "Missing password")
    private String password;

    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "\\d{4}(.| |,)\\d{1,2}(.| |,)\\d{1,2}", message = "Invalid date of birth")
    private String dob;
}
