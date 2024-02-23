package hu.csercsak_albert.banking_system.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}
