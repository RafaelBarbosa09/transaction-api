package com.infnet.transactionapi.domain.Exceptions;

public class InactiveCardException extends RuntimeException {
    public InactiveCardException(String message) {
        super(message);
    }
}
