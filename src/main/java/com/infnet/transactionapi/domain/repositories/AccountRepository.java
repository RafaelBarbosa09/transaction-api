package com.infnet.transactionapi.domain.repositories;

import com.infnet.transactionapi.domain.domainModels.AccountDomain;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    AccountDomain findByAccountHolder(String accountHolder);
    AccountDomain save(AccountDomain account);
    AccountDomain findById(Long id);
    List<AccountDomain> findAll();
}
