package com.infnet.transactionapi.domain.domainModels;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class AccountDomain {
    private String accountHolder;
    private BigDecimal availableLimit;
    private Boolean activeCard;
    private List<TransactionDomain> transactionDomains;

    public AccountDomain() {}

    public AccountDomain(String accountHolder, BigDecimal availableLimit, Boolean activeCard) {
        this.accountHolder = accountHolder;
        this.availableLimit = availableLimit;
        this.activeCard = activeCard;
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

    public List<TransactionDomain> getTransactions() {
        return transactionDomains;
    }

    public void setTransactions(List<TransactionDomain> transactionDomains) {
        this.transactionDomains = transactionDomains;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDomain that = (AccountDomain) o;
        return Objects.equals(accountHolder, that.accountHolder) && Objects.equals(availableLimit, that.availableLimit) && Objects.equals(activeCard, that.activeCard) && Objects.equals(transactionDomains, that.transactionDomains);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountHolder, availableLimit, activeCard, transactionDomains);
    }
}
