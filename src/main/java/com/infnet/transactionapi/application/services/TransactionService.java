package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.SellerDTO;
import com.infnet.transactionapi.application.DTO.TransactionDTO;
import com.infnet.transactionapi.application.mappers.AccountDTOMapper;
import com.infnet.transactionapi.application.mappers.TransactionDTOMapper;
import com.infnet.transactionapi.application.mappers.TransactionMapper;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import com.infnet.transactionapi.domain.factories.AccountFactory;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import com.infnet.transactionapi.domain.repositories.SellerRepository;
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
    private final SellerRepository sellerRepository;
    private final TransactionDTOMapper transactionMapper;
    private final AccountDTOMapper accountMapper;

    public TransactionService(
        TransactionRepository transactionRepository,
        AccountRepository accountRepository,
        SellerRepository sellerRepository,
        TransactionDTOMapper transactionMapper,
        AccountDTOMapper accountMapper
    ) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.sellerRepository = sellerRepository;
        this.transactionMapper = transactionMapper;
        this.accountMapper = accountMapper;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<TransactionDomain> transactions =  transactionRepository.findAll();
        return transactions.stream().map(transactionMapper::toDTO).toList();
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        String accountHolder = transactionDTO.getAccount().getAccountHolder();
        AccountDomain account = accountRepository.findByAccountHolder(accountHolder);

        if (Objects.isNull(account)) {
            throw new RuntimeException("Account not found");
        }

        TransactionDomain transactionDomain = transactionMapper.toDomain(transactionDTO);
        transactionDomain.setAccount(account);
        transactionDomain.setTransactionTime(new Date());

        String sellerName = transactionDTO.getSeller().getName();
        SellerDomain seller = sellerRepository.findByName(sellerName);

        if (Objects.isNull(seller)) {
            seller = sellerRepository.save(transactionDomain.getSeller());
        }

        transactionDomain.setSeller(seller);

        TransactionDomain transaction = transactionRepository.save(transactionDomain);

        return transactionMapper.toDTO(transaction);
    }
}
