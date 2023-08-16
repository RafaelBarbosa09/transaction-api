package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.application.mappers.AccountDTOMapper;
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

    public AccountDomain createAccountDomainFromDTO(AccountDTO accountDTO) {
        return mapper.toDomain(accountDTO);
    }

    public AccountDTO createDTOFromAccountDomain(AccountDomain account) {
        return mapper.toDTO(account);
    }

    public List<AccountDTO> createAccountDTOListFromAccountDomainList(List<AccountDomain> accounts) {
        return accounts.stream().map(this::createDTOFromAccountDomain).toList();
    }

    public List<AccountDomain> getAllAccounts() {
        return accountRepository.findAll();
    }

    public AccountDomain createAccount(AccountDomain account) {
        AccountDomain accountDomain = AccountFactory.create();
        accountDomain.setAccountHolder(account.getAccountHolder());
        return accountRepository.save(accountDomain);
    }

    public AccountDomain getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public AccountDomain getAccountByAccountHolder(String accountHolder) {
        return accountRepository.findByAccountHolder(accountHolder);
    }
}
