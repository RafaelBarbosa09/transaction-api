package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.dto.TransactionDTO;
import com.infnet.transactionapi.application.mappers.TransactionDTOMapper;
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
        AccountDomain account = findTransactionAccount(accountHolder);

        TransactionDomain transactionDomain = transactionMapper.toDomain(transactionDTO);
        transactionDomain.setTransactionTime(new Date());
        transactionDomain.setAccount(account);

        String sellerName = transactionDTO.getSeller().getName();
        SellerDomain seller = findOrCreateTransactionSeller(sellerName);
        transactionDomain.setSeller(seller);

        List<TransactionDomain> transactions = transactionRepository.findAll();
        transactionDomain.authorizeTransaction(transactions);

        TransactionDomain transaction = transactionRepository.save(transactionDomain);

        AccountDomain accountUpdated = updateTransactionAccountLimit(account, transaction.getValue());
        transaction.setAccount(accountUpdated);

        return transactionMapper.toDTO(transaction);
    }

    private AccountDomain findTransactionAccount(String accountHolder) {
        AccountDomain account = accountRepository.findByAccountHolder(accountHolder);
        if (Objects.isNull(account)) {
            throw new NotFoundException("Account not found");
        }

        return account;
    }

    public AccountDomain updateTransactionAccountLimit(AccountDomain account, BigDecimal value) {
        AccountDomain accountToUpdate = accountRepository.findById(account.getId());
        accountToUpdate.setAvailableLimit(accountToUpdate.getAvailableLimit().subtract(value));
        return accountRepository.update(accountToUpdate);
    }

    private SellerDomain findOrCreateTransactionSeller(String sellerName) {
        SellerDomain seller = sellerRepository.findByName(sellerName);
        if (Objects.isNull(seller)) {
            seller = newTransactionSeller(sellerName);
        }

        return seller;
    }

    private SellerDomain newTransactionSeller(String name) {
        SellerDomain seller = new SellerDomain();
        seller.setName(name);

        return sellerRepository.save(seller);
    }
}
