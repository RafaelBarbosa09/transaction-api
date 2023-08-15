package com.infnet.transactionapi.domain.entities;

import java.math.BigDecimal;
import java.util.List;

public class Account {
    private String accountHolder;
    private BigDecimal availableLimit;
    private Boolean activeCard;
    private List<Transaction> transactions;

    public Account() {}

    public Account(String accountHolder, BigDecimal availableLimit, Boolean activeCard, List<Transaction> transactions) {
        this.accountHolder = accountHolder;
        this.availableLimit = availableLimit;
        this.activeCard = activeCard;
        this.transactions = transactions;
    }

    public Account create(Account account) {
        this.accountHolder = account.accountHolder;
        this.availableLimit = new BigDecimal(1200.0);
        this.activeCard = true;
        return this;
    }

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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
