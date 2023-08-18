package com.infnet.transactionapi.domain.domainModels;

import com.infnet.transactionapi.domain.Exceptions.DoubleTransactionException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class TransactionDomain {
    private Long id;
    private BigDecimal value;
    private Date transactionTime;
    private Long quantity;
    private AccountDomain account;
    private SellerDomain seller;

    public TransactionDomain() {}

    public TransactionDomain(BigDecimal value, Date transactionTime, Long quantity, AccountDomain account, SellerDomain seller) {
        this.value = value;
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

    public void checkDoubleTransaction(BigDecimal amount, Date transactionTime, SellerDomain sellerDomain) {
        if (amount == this.value && transactionTime == this.transactionTime && sellerDomain == this.seller) new DoubleTransactionException("Double transaction");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public AccountDomain getAccount() {
        return account;
    }

    public void setAccount(AccountDomain accountDomain) {
        this.account = accountDomain;
    }

    public SellerDomain getSeller() {
        return seller;
    }

    public void setSeller(SellerDomain sellerDomain) {
        this.seller = sellerDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDomain that = (TransactionDomain) o;
        return Objects.equals(value, that.value) && Objects.equals(transactionTime, that.transactionTime) && Objects.equals(quantity, that.quantity) && Objects.equals(account, that.account) && Objects.equals(seller, that.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, transactionTime, quantity, account, seller);
    }
}
