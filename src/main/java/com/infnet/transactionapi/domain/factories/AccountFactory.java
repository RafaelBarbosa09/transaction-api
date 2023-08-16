package com.infnet.transactionapi.domain.factories;

import com.infnet.transactionapi.domain.domainModels.AccountDomain;

import java.math.BigDecimal;

public class AccountFactory {
    public static AccountDomain create(String accountHolder) {
        AccountDomain account = new AccountDomain();
        account.setAccountHolder(accountHolder);
        account.setAvailableLimit(new BigDecimal("1200.00"));
        account.setActiveCard(true);
        return account;
    }
}
