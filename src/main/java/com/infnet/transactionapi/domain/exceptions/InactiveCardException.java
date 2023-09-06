package com.infnet.transactionapi.domain.exceptions;

public class InactiveCardException extends RuntimeException {
    public InactiveCardException(String message) {
        super(message);
    }
}
