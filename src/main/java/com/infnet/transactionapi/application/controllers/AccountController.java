package com.infnet.transactionapi.application.controllers;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.application.services.AccountService;
import com.infnet.transactionapi.infrastructure.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<Object> createAccount(@RequestBody AccountDTO accountDTO) {
        try {
            Account account = accountService.createAccountFromDTO(accountDTO);
            Account accountCreated = accountService.createAccount(account);

            return new ResponseEntity<>(accountCreated, null, 201);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id) {
        try {
            Account account = accountService.getAccountById(id);
            AccountDTO accountFounded = accountService.createDTOFromAccount(account);

            return new ResponseEntity<>(accountFounded, null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }
}
