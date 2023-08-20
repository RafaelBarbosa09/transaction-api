package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.TransactionDTO;
import com.infnet.transactionapi.application.mappers.TransactionDTOMapper;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import com.infnet.transactionapi.domain.repositories.SellerRepository;
import com.infnet.transactionapi.domain.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final SellerRepository sellerRepository;
    private final TransactionDTOMapper transactionMapper;

    public TransactionService(
        TransactionRepository transactionRepository,
        AccountRepository accountRepository,
        TransactionDTOMapper transactionMapper,
        SellerRepository sellerRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionMapper = transactionMapper;
        this.sellerRepository = sellerRepository;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<TransactionDomain> transactions =  transactionRepository.findAll();
        return transactions.stream().map(transactionMapper::toDTO).toList();
    }

    public TransactionDTO getTransactionById(Long id) {
        TransactionDomain transaction = transactionRepository.findById(id);
        if (Objects.isNull(transaction)) {
            throw new NotFoundException("Transaction not found");
        }

        return transactionMapper.toDTO(transaction);
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        String accountHolder = transactionDTO.getAccount().getAccountHolder();
        AccountDomain account = accountRepository.findByAccountHolder(accountHolder);
        if (Objects.isNull(account)) {
            throw new NotFoundException("Account not found");
        }
        account.setAvailableLimit(account.getAvailableLimit().subtract(transactionDTO.getValue()));

        TransactionDomain transactionDomain = transactionMapper.toDomain(transactionDTO);
        transactionDomain.setAccount(account);
        transactionDomain.setTransactionTime(new Date());

        SellerDomain seller = sellerRepository.findByName(transactionDomain.getSeller().getName());
        if (!Objects.isNull(seller)) {
            transactionDomain.setSeller(seller);
        }

        List<TransactionDomain> transactions = transactionRepository.findAll();
        transactionDomain.authorizeTransaction(transactions);

        TransactionDomain transaction = transactionRepository.save(transactionDomain);
        AccountDomain account2 = accountRepository.findByAccountHolder(accountHolder);
        account2.setAvailableLimit(account.getAvailableLimit());
        accountRepository.update(account2);

        return transactionMapper.toDTO(transaction);
    }
}
