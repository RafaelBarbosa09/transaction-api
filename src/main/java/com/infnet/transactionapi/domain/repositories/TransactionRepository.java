package com.infnet.transactionapi.domain.repositories;
import com.infnet.transactionapi.domain.domainModels.Transaction;

import java.util.List;

public interface TransactionRepository {
    Transaction findByTransactionHolder(String transactionHolder);
    Transaction save(Transaction transaction);
    Transaction findById(Long id);
    List<Transaction> findAll();
}
