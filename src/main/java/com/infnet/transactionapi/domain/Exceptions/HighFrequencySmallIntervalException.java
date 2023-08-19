package com.infnet.transactionapi.domain.Exceptions;

public class HighFrequencySmallIntervalException extends RuntimeException {
    public HighFrequencySmallIntervalException(String message) {
        super(message);
    }
}
