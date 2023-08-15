package com.infnet.transactionapi.application.DTO;

import com.infnet.transactionapi.infrastructure.models.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class AccountDTO {
    private String accountHolder;
    private BigDecimal availableLimit;
    private Boolean activeCard;

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public BigDecimal getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(BigDecimal availableLimit) {
        this.availableLimit = availableLimit;
    }

    public Boolean getActiveCard() {
        return activeCard;
    }

    public void setActiveCard(Boolean activeCard) {
        this.activeCard = activeCard;
    }
}
