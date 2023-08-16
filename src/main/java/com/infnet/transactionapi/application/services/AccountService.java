package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDomain createAccountDomainFromDTO(AccountDTO accountDTO) {
        AccountDomain account = new AccountDomain();
        account.setAccountHolder(accountDTO.getAccountHolder());
        account.setAvailableLimit(new BigDecimal(1200.00));
        account.setActiveCard(true);
        return account;
    }

    public AccountDTO createDTOFromAccountDomain(AccountDomain account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountHolder(account.getAccountHolder());
        accountDTO.setAvailableLimit(account.getAvailableLimit());
        accountDTO.setActiveCard(account.getActiveCard());
        return accountDTO;
    }

    public List<AccountDTO> createAccountDTOListFromAccountDomainList(List<AccountDomain> accounts) {
        return accounts.stream().map(this::createDTOFromAccountDomain).toList();
    }

    public List<AccountDomain> getAllAccounts() {
        return accountRepository.findAll();
    }

    public AccountDomain createAccount(AccountDomain account) {
        return accountRepository.save(account);
    }

    public AccountDomain getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public AccountDomain getAccountByAccountHolder(String accountHolder) {
        return accountRepository.findByAccountHolder(accountHolder);
    }
}
