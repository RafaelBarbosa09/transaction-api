package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.domain.Exceptions.ResourceNotFoundException;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import com.infnet.transactionapi.infrastructure.models.Account;
//import com.infnet.transactionapi.infrastructure.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccountFromDTO(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccountHolder(accountDTO.getAccountHolder());
        account.setAvailableLimit(new BigDecimal(1200.00));
        account.setActiveCard(true);
        return account;
    }

    public AccountDTO createDTOFromAccount(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountHolder(account.getAccountHolder());
        accountDTO.setAvailableLimit(account.getAvailableLimit());
        accountDTO.setActiveCard(account.getActiveCard());
        return accountDTO;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account getAccountByAccountHolder(String accountHolder) {
        return accountRepository.findByAccountHolder(accountHolder);
    }
}
