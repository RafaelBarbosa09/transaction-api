package com.infnet.transactionapi.infrastructure.persistence;

import com.infnet.transactionapi.application.mappers.AccountMapper;
import com.infnet.transactionapi.domain.entities.AccountDomain;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import com.infnet.transactionapi.domain.repositories.JpaAccountRepository;
import com.infnet.transactionapi.infrastructure.models.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository repository;
    private final AccountMapper mapper;

    public AccountRepositoryImpl(JpaAccountRepository repository, AccountMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AccountDomain findByAccountHolder(String accountHolder) {
        Account account = repository.findByAccountHolder(accountHolder);
        AccountDomain accountDomain = mapper.toEntity(account);
        return accountDomain;
    }

    @Override
    public AccountDomain save(AccountDomain account) {
        Account accountModel = mapper.toModel(account);
        Account accountSaved = repository.save(accountModel);
        AccountDomain accountDomain = mapper.toEntity(accountSaved);
        return accountDomain;
    }

    @Override
    public AccountDomain findById(Long id) {
        Account account = repository.findById(id).orElse(null);
        AccountDomain accountDomain = mapper.toEntity(account);
        return accountDomain;
    }

    @Override
    public List<AccountDomain> findAll() {
        List<Account> accounts = repository.findAll();
        List<AccountDomain> accountsDomain = accounts.stream().map(account -> mapper.toEntity(account)).toList();
        return accountsDomain;
    }
}
