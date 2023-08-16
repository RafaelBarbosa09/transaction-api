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

    public AccountDomain createAccountDomainFromDTO(AccountDTO accountDTO) {
        return mapper.toDomain(accountDTO);
    }

    public AccountDTO createDTOFromAccountDomain(AccountDomain account) {
        return mapper.toDTO(account);
    }

    public List<AccountDTO> createAccountDTOListFromAccountDomainList(List<AccountDomain> accounts) {
        return accounts.stream().map(this::createDTOFromAccountDomain).toList();
    }

    public List<AccountDTO> getAllAccounts() {
        return createAccountDTOListFromAccountDomainList(accountRepository.findAll());
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        AccountDomain accountDomain = createAccountDomainFromDTO(accountDTO);

        if (accountAlreadyExists(accountDomain)) {
            throw new AccountAlreadyExistsException("Account already exists");
        }

        AccountDomain account = AccountFactory.create(accountDomain.getAccountHolder());
        return createDTOFromAccountDomain(accountRepository.save(account));
    }

    public Boolean accountAlreadyExists(AccountDomain account) {
        AccountDomain accountDomain = accountRepository.findByAccountHolder(account.getAccountHolder());
        return accountDomain != null;
    }

    public AccountDTO getAccountById(Long id) {
        return createDTOFromAccountDomain(accountRepository.findById(id));
    }

    public AccountDTO getAccountByAccountHolder(String accountHolder) {
        return createDTOFromAccountDomain(accountRepository.findByAccountHolder(accountHolder));
    }
}
