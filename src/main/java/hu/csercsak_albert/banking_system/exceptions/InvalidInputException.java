package hu.csercsak_albert.banking_system.exceptions;

import java.io.Serial;

public class InvalidInputException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5099606771615272909L;

    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
