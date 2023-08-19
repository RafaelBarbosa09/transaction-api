package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.TransactionDTO;
import com.infnet.transactionapi.application.mappers.AccountDTOMapper;
import com.infnet.transactionapi.application.mappers.SellerDTOMapper;
import com.infnet.transactionapi.application.mappers.TransactionDTOMapper;
import com.infnet.transactionapi.domain.Exceptions.DuplicateTransactionException;
import com.infnet.transactionapi.domain.Exceptions.HighFrequencySmallIntervalException;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import com.infnet.transactionapi.domain.repositories.SellerRepository;
import com.infnet.transactionapi.domain.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
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
    private final SellerDTOMapper sellerMapper;

    public TransactionService(
        TransactionRepository transactionRepository,
        AccountRepository accountRepository,
        SellerRepository sellerRepository,
        TransactionDTOMapper transactionMapper,
        AccountDTOMapper accountMapper,
        SellerDTOMapper sellerMapper
    ) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.sellerRepository = sellerRepository;
        this.transactionMapper = transactionMapper;
        this.accountMapper = accountMapper;
        this.sellerMapper = sellerMapper;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<TransactionDomain> transactions =  transactionRepository.findAll();
        return transactions.stream().map(transactionMapper::toDTO).toList();
    }

    public TransactionDTO getTransactionById(Long id) {
        TransactionDomain transaction = transactionRepository.findById(id);
        if (Objects.isNull(transaction)) {
            throw new RuntimeException("Transaction not found");
        }

        return transactionMapper.toDTO(transaction);
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        String accountHolder = transactionDTO.getAccount().getAccountHolder();
        AccountDomain account = accountRepository.findByAccountHolder(accountHolder);

        if (Objects.isNull(account)) {
            throw new NotFoundException("Account not found");
        }

        TransactionDomain transactionDomain = transactionMapper.toDomain(transactionDTO);
        transactionDomain.setAccount(account);
        transactionDomain.setTransactionTime(new Date());

        SellerDomain seller = createSellerIfNotExists(transactionDomain);
        transactionDomain.setSeller(seller);

        List<TransactionDomain> transactions = transactionRepository.findAll();
        transactionDomain.authorizeTransaction(transactions);

        TransactionDomain transaction = transactionRepository.save(transactionDomain);

        return transactionMapper.toDTO(transaction);
    }

    private SellerDomain createSellerIfNotExists(TransactionDomain transactionDomain) {
        String sellerName = transactionDomain.getSeller().getName();
        SellerDomain seller = sellerRepository.findByName(sellerName);

        if (Objects.isNull(seller)) {
            seller = sellerRepository.save(transactionDomain.getSeller());
        }

        return seller;
    }
}
