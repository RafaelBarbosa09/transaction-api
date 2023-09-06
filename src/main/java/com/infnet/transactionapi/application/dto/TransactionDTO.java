package com.infnet.transactionapi.application.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDTO {
    private BigDecimal value;
    private Date transactionTime;
    private AccountDTO account;
    private SellerDTO seller;

    public TransactionDTO() {}

    public TransactionDTO(BigDecimal value, Date transactionTime, AccountDTO account, SellerDTO seller) {
        this.value = value;
        this.transactionTime = transactionTime;
        this.account = account;
        this.seller = seller;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }
}
