package com.infnet.transactionapi.domain.Exceptions;

public class LimitNotAvailableException extends RuntimeException{
    public LimitNotAvailableException(String message) {
        super(message);
    }
}
