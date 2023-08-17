package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.application.mappers.AccountDTOMapper;
import com.infnet.transactionapi.domain.Exceptions.AccountAlreadyExistsException;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.factories.AccountFactory;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountDTOMapper mapper;

    public AccountService(AccountRepository accountRepository, AccountDTOMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    public List<AccountDTO> getAllAccounts() {
        List<AccountDomain> accounts = accountRepository.findAll();
        return accounts.stream().map(mapper::toDTO).toList();
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        AccountDomain accountDomain = this.mapper.toDomain(accountDTO);

        if (accountAlreadyExists(accountDomain)) {
            throw new AccountAlreadyExistsException("Account already exists");
        }

        AccountDomain account = AccountFactory.create(accountDomain.getAccountHolder());
        return this.mapper.toDTO(accountRepository.save(account));
    }

    public Boolean accountAlreadyExists(AccountDomain account) {
        AccountDomain accountDomain = accountRepository.findByAccountHolder(account.getAccountHolder());
        return accountDomain != null;
    }

    public AccountDTO getAccountById(Long id) {
        return this.mapper.toDTO(accountRepository.findById(id));
    }

    public AccountDTO getAccountByAccountHolder(String accountHolder) {
        return this.mapper.toDTO(accountRepository.findByAccountHolder(accountHolder));
    }
}
