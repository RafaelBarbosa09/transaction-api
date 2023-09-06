package com.infnet.transactionapi.domain.exceptions;

public class HighFrequencySmallIntervalException extends RuntimeException {
    public HighFrequencySmallIntervalException(String message) {
        super(message);
    }
}
