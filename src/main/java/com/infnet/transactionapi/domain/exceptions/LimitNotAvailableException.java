package com.infnet.transactionapi.domain.exceptions;

public class LimitNotAvailableException extends RuntimeException{
    public LimitNotAvailableException(String message) {
        super(message);
    }
}
