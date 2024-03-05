package hu.csercsak_albert.banking_system.security.password;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Check individual requirements and construct error messages
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Password must ");

        if (password.length() < 8) {
            errorMessage.append("be at least 8 characters long, ");
            isValid = false;
        }

        if (!password.matches(".*[a-z].*")) {
            errorMessage.append("contain at least one lowercase letter, ");
            isValid = false;
        }

        if (!password.matches(".*[A-Z].*")) {
            errorMessage.append("contain at least one uppercase letter, ");
            isValid = false;
        }

        if (!password.matches(".*\\d.*")) {
            errorMessage.append("contain at least one digit, ");
            isValid = false;
        }

        if (!password.matches(".*[@$!%*?&].*")) {
            errorMessage.append("contain at least one special character, ");
            isValid = false;
        }

        errorMessage.deleteCharAt(errorMessage.length() - 2); // Remove the extra comma and space

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
