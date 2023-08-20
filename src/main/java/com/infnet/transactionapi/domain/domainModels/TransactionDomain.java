package com.infnet.transactionapi.domain.domainModels;

import com.infnet.transactionapi.domain.Exceptions.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

    public void authorizeTransaction(List<TransactionDomain> transactions) {
        if(isHighFrequencySmallInterval(transactions)) {
            throw new HighFrequencySmallIntervalException("High frequency small interval");
        }

        if(hasDuplicateTransactions(transactions, this)) {
            throw new DuplicateTransactionException("Similar transaction");
        }

        if(!isActiveCard()) {
            throw new InactiveCardException("Inactive card");
        }

        if(!isLimitAvailable()) {
            throw new LimitNotAvailableException("Limit not available");
        }
    }

    private boolean isActiveCard() {
        return this.account.getActiveCard();
    }

    private boolean isLimitAvailable() {
        return this.account.getAvailableLimit().compareTo(this.value) >= 0;
    }

    private boolean isHighFrequencySmallInterval(List<TransactionDomain> transactions) {
        Date now = new Date();
        final long TWO_MINUTES_IN_MILLISECONDS = 2 * 60 * 1000;
        int count = 0;

        for (TransactionDomain transaction : transactions) {
            if (transaction.isWithinTwoMinutes(transaction.getTransactionTime(), now, TWO_MINUTES_IN_MILLISECONDS)) {
                count++;
                if (count >= 3) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasDuplicateTransactions(List<TransactionDomain> transactions, TransactionDomain transaction) {
        final long TWO_MINUTES_IN_MILLISECONDS = 2 * 60 * 1000;
        Date now = new Date();
        int count = 0;

        for (TransactionDomain transactionDomain : transactions) {
            if (isWithinTwoMinutes(transactionDomain.getTransactionTime(), now, TWO_MINUTES_IN_MILLISECONDS) &&
                isSimilarTransaction(transactionDomain, transaction)) {
                count++;
                if (count >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSimilarTransaction(TransactionDomain firstTransaction, TransactionDomain secondTransaction) {
        boolean isSameSeller = firstTransaction.getSeller().getName().equals(secondTransaction.getSeller().getName());
        boolean isSameValue = firstTransaction.getValue().equals(secondTransaction.getValue());

        return isSameValue && isSameSeller;
    }

    private boolean isWithinTwoMinutes(Date transactionTime, Date currentTime, long timeLimitMillis) {
        long timeDifferenceMillis = currentTime.getTime() - transactionTime.getTime();
        return timeDifferenceMillis <= timeLimitMillis;
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
