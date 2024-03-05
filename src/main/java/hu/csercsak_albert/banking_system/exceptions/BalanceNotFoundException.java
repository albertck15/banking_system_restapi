package hu.csercsak_albert.banking_system.exceptions;

import java.io.Serial;

public class BalanceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1639756089260606072L;

    public BalanceNotFoundException() {
    }

    public BalanceNotFoundException(String message) {
        super(message);
    }
}
