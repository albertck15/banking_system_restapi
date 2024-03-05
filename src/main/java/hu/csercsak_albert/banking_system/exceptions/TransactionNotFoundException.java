package hu.csercsak_albert.banking_system.exceptions;

import java.io.Serial;

public class TransactionNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7748503738759187904L;

    public TransactionNotFoundException() {
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
