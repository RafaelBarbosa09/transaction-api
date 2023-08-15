package com.infnet.transactionapi.domain.entities;

import com.infnet.transactionapi.domain.Exceptions.DoubleTransactionException;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private BigDecimal amount;
    private Date transactionTime;
    private Long quantity;
    private Account account;
    private Seller seller;

    public Transaction() {}

    public Transaction(BigDecimal amount, Date transactionTime, Long quantity, Account account, Seller seller) {
        this.amount = amount;
        this.transactionTime = transactionTime;
        this.quantity = quantity;
        this.account = account;
        this.seller = seller;
    }

    public Boolean isCardActive(Boolean activeCard) {
        return activeCard;
    }

    public Boolean isLimitAvailable(BigDecimal amount) {
        return this.account.getAvailableLimit().compareTo(amount) >= 0;
    }

    public Boolean authorizeTransaction(Boolean isCardActive, Boolean isLimitAvailable) {
        return isCardActive && isLimitAvailable;
    }

    public void checkHighFrequencySmallInterval(Date transactionTime, long quantity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void checkDoubleTransaction(BigDecimal amount, Date transactionTime, Seller seller) {
        if (amount == this.amount && transactionTime == this.transactionTime && seller == this.seller) new DoubleTransactionException("Double transaction");
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
