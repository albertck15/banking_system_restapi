package hu.csercsak_albert.banking_system.exceptions;

public class BalanceNotFoundException extends RuntimeException {
    public BalanceNotFoundException() {
    }

    public BalanceNotFoundException(String message) {
        super(message);
    }
}
