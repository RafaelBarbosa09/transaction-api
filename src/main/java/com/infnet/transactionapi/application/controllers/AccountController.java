package com.infnet.transactionapi.application.controllers;

import com.infnet.transactionapi.application.dto.AccountDTO;
import com.infnet.transactionapi.application.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Accounts")
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<Object> getAllAccounts() {
        try {
            List<AccountDTO> accounts = accountService.getAllAccounts();
            return ResponseEntity.status(HttpStatus.OK).body(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createAccount(@RequestBody AccountDTO accountDTO) {
        try {
            AccountDTO account = accountService.createAccount(accountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id) {
        try {
            AccountDTO account = accountService.getAccountById(id);
            return ResponseEntity.status(HttpStatus.OK).body(account);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/holder/{accountHolder}")
    public ResponseEntity<Object> getAccountByAccountHolder(@PathVariable String accountHolder) {
        try {
            AccountDTO account = accountService.getAccountByAccountHolder(accountHolder);
            return ResponseEntity.status(HttpStatus.OK).body(account);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccountById(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
