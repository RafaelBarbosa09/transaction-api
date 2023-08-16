package com.infnet.transactionapi.application.controllers;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.application.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Account", description = "Account API")
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<Object> getAllAccounts() {
        try {
            List<AccountDTO> accounts = accountService.getAllAccounts();
            return new ResponseEntity<>(accounts, null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createAccount(@RequestBody AccountDTO accountDTO) {
        try {
            AccountDTO account = accountService.createAccount(accountDTO);
            return new ResponseEntity<>(account, null, 201);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id) {
        try {
            AccountDTO account = accountService.getAccountById(id);
            return new ResponseEntity<>(account, null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }

    @GetMapping("/holder/{accountHolder}")
    public ResponseEntity<Object> getAccountByAccountHolder(@PathVariable String accountHolder) {
        try {
            AccountDTO account = accountService.getAccountByAccountHolder(accountHolder);
            return new ResponseEntity<>(account, null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }
}
