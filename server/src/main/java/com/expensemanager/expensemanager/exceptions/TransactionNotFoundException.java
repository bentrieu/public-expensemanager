package com.expensemanager.expensemanager.exceptions;

import java.io.Serial;

public class TransactionNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
