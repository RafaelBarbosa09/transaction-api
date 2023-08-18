package com.infnet.transactionapi.infrastructure.persistence;

import com.infnet.transactionapi.application.mappers.TransactionMapper;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import com.infnet.transactionapi.domain.repositories.JpaTransactionRepository;
import com.infnet.transactionapi.domain.repositories.TransactionRepository;
import com.infnet.transactionapi.infrastructure.entities.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JpaTransactionRepository repository;
    private final TransactionMapper mapper;

    public TransactionRepositoryImpl(JpaTransactionRepository repository, TransactionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<TransactionDomain> findAll() {
        List<Transaction> transactions = repository.findAll();
        return transactions.stream().map(mapper::toDomain).toList();
    }

    @Transactional
    @Override
    public TransactionDomain save(TransactionDomain transaction) {
        Transaction transactionEntity = mapper.toEntity(transaction);
        Transaction transactionSaved = repository.save(transactionEntity);
        return mapper.toDomain(transactionSaved);
    }
}
