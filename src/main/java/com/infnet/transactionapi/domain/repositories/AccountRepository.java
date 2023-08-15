package com.infnet.transactionapi.domain.repositories;

import com.infnet.transactionapi.infrastructure.models.Account;

import java.util.List;

public interface AccountRepository {
    Account findByAccountHolder(String accountHolder);
    Account save(Account account);
    Account findById(Long id);
    List<Account> findAll();
}
