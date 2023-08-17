package com.infnet.transactionapi.application.DTO;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
public class AccountDTO {
    private String accountHolder;
    private BigDecimal availableLimit;
    private Boolean activeCard;

    public AccountDTO() {}

    public AccountDTO(String accountHolder, BigDecimal availableLimit, Boolean activeCard) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(accountHolder, that.accountHolder) && Objects.equals(availableLimit, that.availableLimit) && Objects.equals(activeCard, that.activeCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountHolder, availableLimit, activeCard);
    }
}
