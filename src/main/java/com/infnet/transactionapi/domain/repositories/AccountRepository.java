package com.infnet.transactionapi.domain.repositories;

import com.infnet.transactionapi.domain.domainModels.AccountDomain;

import java.util.List;

public interface AccountRepository {
    AccountDomain findByAccountHolder(String accountHolder);
    AccountDomain save(AccountDomain account);
    AccountDomain update(AccountDomain account);
    AccountDomain findById(Long id);
    List<AccountDomain> findAll();
    void deleteById(Long id);
}
