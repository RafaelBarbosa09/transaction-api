package com.infnet.transactionapi.domain.Exceptions;

public class DoubleTransactionException extends Exception {
    public DoubleTransactionException(String message) {
        super(message);
    }
}
