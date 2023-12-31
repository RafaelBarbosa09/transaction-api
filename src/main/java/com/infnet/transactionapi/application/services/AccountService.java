package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.dto.AccountDTO;
import com.infnet.transactionapi.application.mappers.AccountDTOMapper;
import com.infnet.transactionapi.domain.exceptions.AccountAlreadyExistsException;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.exceptions.ExceptionMessages;
import com.infnet.transactionapi.domain.factories.AccountFactory;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Objects;

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
            throw new AccountAlreadyExistsException(ExceptionMessages.ACCOUNT_ALREADY_EXISTS);
        }

        AccountDomain account = AccountFactory.create(accountDomain.getAccountHolder());
        return this.mapper.toDTO(accountRepository.save(account));
    }

    public boolean accountAlreadyExists(AccountDomain account) {
        AccountDomain accountDomain = accountRepository.findByAccountHolder(account.getAccountHolder());
        return Objects.nonNull(accountDomain);
    }

    public AccountDTO getAccountById(Long id) {
        AccountDomain account = accountRepository.findById(id);
        validateAccountIsNotNull(account);

        return this.mapper.toDTO(account);
    }

    public AccountDTO getAccountByAccountHolder(String accountHolder) {
        AccountDomain account = accountRepository.findByAccountHolder(accountHolder);
        validateAccountIsNotNull(account);

        return this.mapper.toDTO(account);
    }

    public void deleteAccount(Long id) {
        AccountDomain account = accountRepository.findById(id);
        validateAccountIsNotNull(account);

        accountRepository.deleteById(id);
    }

    private void validateAccountIsNotNull(AccountDomain account) {
        if (Objects.isNull(account)) {
            throw new NotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND);
        }
    }
}
