package com.infnet.transactionapi.infrastructure.datasources;

import com.infnet.transactionapi.application.mappers.AccountMapper;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import com.infnet.transactionapi.domain.repositories.JpaAccountRepository;
import com.infnet.transactionapi.infrastructure.entities.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        return mapper.toDomain(account);
    }

    @Transactional
    @Override
    public AccountDomain save(AccountDomain account) {
        Account accountEntity = mapper.toEntity(account);
        Account accountSaved = repository.save(accountEntity);
        return mapper.toDomain(accountSaved);
    }

    @Override
    public AccountDomain update(AccountDomain account) {
        return this.save(account);
    }


    @Override
    public AccountDomain findById(Long id) {
        Account account = repository.findById(id).orElse(null);
        return mapper.toDomain(account);
    }

    @Override
    public List<AccountDomain> findAll() {
        List<Account> accounts = repository.findAll();
        return accounts.stream().map(mapper::toDomain).toList();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
