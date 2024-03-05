package hu.csercsak_albert.banking_system.exceptions;

import java.io.Serial;

public class InvalidAmountException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5306681969728584104L;

    public InvalidAmountException() {
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}
