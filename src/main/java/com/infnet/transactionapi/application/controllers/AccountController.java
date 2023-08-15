package com.infnet.transactionapi.application.controllers;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.application.services.AccountService;
import com.infnet.transactionapi.infrastructure.models.Account;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account", description = "Account API")
@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<Object> getAllAccounts() {
        try {
            return new ResponseEntity<>(accountService.getAllAccounts(), null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
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

    @GetMapping("/holder/{accountHolder}")
    public ResponseEntity<Object> getAccountByAccountHolder(@PathVariable String accountHolder) {
        try {
            Account account = accountService.getAccountByAccountHolder(accountHolder);
            AccountDTO accountFounded = accountService.createDTOFromAccount(account);

            return new ResponseEntity<>(accountFounded, null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }
}
