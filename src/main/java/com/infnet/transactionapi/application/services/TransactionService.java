package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import com.infnet.transactionapi.domain.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDomain> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
