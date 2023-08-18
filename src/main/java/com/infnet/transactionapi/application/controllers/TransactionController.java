package com.infnet.transactionapi.application.controllers;

import com.infnet.transactionapi.application.DTO.TransactionDTO;
import com.infnet.transactionapi.application.services.TransactionService;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
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

    @PostMapping()
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO transaction = transactionService.createTransaction(transactionDTO);
            return new ResponseEntity<>(transaction, null, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
