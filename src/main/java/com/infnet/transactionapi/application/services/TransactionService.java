package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.SellerDTO;
import com.infnet.transactionapi.application.DTO.TransactionDTO;
import com.infnet.transactionapi.application.mappers.AccountDTOMapper;
import com.infnet.transactionapi.application.mappers.TransactionDTOMapper;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import com.infnet.transactionapi.domain.factories.AccountFactory;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import com.infnet.transactionapi.domain.repositories.TransactionRepository;
import com.infnet.transactionapi.infrastructure.entities.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionDTOMapper transactionMapper;
    private final AccountDTOMapper accountMapper;

    public TransactionService(TransactionRepository transactionRepository,
        TransactionDTOMapper transactionMapper,
        AccountDTOMapper accountMapper,
        AccountRepository accountRepository) {

        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }

    public List<TransactionDomain> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        String accountHolder = transactionDTO.getAccount().getAccountHolder();
        AccountDomain account = accountRepository.findByAccountHolder(accountHolder);

        if (Objects.isNull(account)) {
            account = this.createAccountIfNotExists(accountHolder);
        }

        TransactionDomain transactionDomain = transactionMapper.toDomain(transactionDTO);
        transactionDomain.setAccount(account);

        SellerDomain seller = new SellerDomain(transactionDomain.getSeller().getName());

        transactionDomain.setSeller(seller);

        TransactionDomain transaction = transactionRepository.save(transactionDomain);

        return transactionMapper.toDTO(transaction);
    }

    public AccountDomain createAccountIfNotExists(String accountHolder) {
        AccountDomain account = AccountFactory.create(accountHolder);
        return accountRepository.save(account);
    }

    public Date parseStringToLocalDateTime(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return formatter.parse(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
