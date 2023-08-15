package com.infnet.transactionapi.infrastructure.persistence;

import com.infnet.transactionapi.domain.repositories.AccountRepository;
import com.infnet.transactionapi.domain.repositories.JpaAccountRepository;
import com.infnet.transactionapi.infrastructure.models.Account;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository repository;

    public AccountRepositoryImpl(JpaAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account findByAccountHolder(String accountHolder) {
        return repository.findByAccountHolder(accountHolder);
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }
}
