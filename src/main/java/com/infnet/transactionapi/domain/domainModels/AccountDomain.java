package com.infnet.transactionapi.domain.domainModels;

import java.math.BigDecimal;
import java.util.List;

public class AccountDomain {
    private String accountHolder;
    private BigDecimal availableLimit;
    private Boolean activeCard;
    private List<TransactionDomain> transactionDomains;

    public AccountDomain() {}

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

    public List<TransactionDomain> getTransactions() {
        return transactionDomains;
    }

    public void setTransactions(List<TransactionDomain> transactionDomains) {
        this.transactionDomains = transactionDomains;
    }
}
