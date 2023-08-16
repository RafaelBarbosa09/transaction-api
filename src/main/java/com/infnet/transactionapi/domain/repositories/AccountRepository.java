package com.infnet.transactionapi.domain.repositories;

import com.infnet.transactionapi.domain.entities.AccountDomain;
import com.infnet.transactionapi.infrastructure.models.Account;

import java.util.List;

public interface AccountRepository {
    AccountDomain findByAccountHolder(String accountHolder);
    AccountDomain save(AccountDomain account);
    AccountDomain findById(Long id);
    List<AccountDomain> findAll();
}
