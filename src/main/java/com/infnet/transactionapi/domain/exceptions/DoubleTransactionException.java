package com.infnet.transactionapi.domain.exceptions;

public class DoubleTransactionException extends Exception {
    public DoubleTransactionException(String message) {
        super(message);
    }
}
