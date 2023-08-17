package com.infnet.transactionapi.application.controllers;

import com.infnet.transactionapi.application.services.TransactionService;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping()
    public ResponseEntity<Object> getAllTransactions() {
        try {
            List<TransactionDomain> transactions = transactionService.getAllTransactions();
            return new ResponseEntity<>(transactions, null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }
}