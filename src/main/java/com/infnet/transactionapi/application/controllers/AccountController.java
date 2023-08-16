package com.infnet.transactionapi.application.controllers;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.application.services.AccountService;
import com.infnet.transactionapi.domain.entities.AccountDomain;
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
            List<AccountDomain> accounts = accountService.getAllAccounts();
            List<AccountDTO> accountsDTO = accountService.createAccountDTOListFromAccountDomainList(accounts);
            return new ResponseEntity<>(accountsDTO, null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createAccount(@RequestBody AccountDTO accountDTO) {
        try {
            AccountDomain accountDomain = accountService.createAccountDomainFromDTO(accountDTO);
            AccountDomain accountCreated = accountService.createAccount(accountDomain);
            AccountDTO response = accountService.createDTOFromAccountDomain(accountCreated);

            return new ResponseEntity<>(response, null, 201);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id) {
        try {
            AccountDomain account = accountService.getAccountById(id);
            AccountDTO accountFounded = accountService.createDTOFromAccountDomain(account);

            return new ResponseEntity<>(accountFounded, null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }

    @GetMapping("/holder/{accountHolder}")
    public ResponseEntity<Object> getAccountByAccountHolder(@PathVariable String accountHolder) {
        try {
            AccountDomain account = accountService.getAccountByAccountHolder(accountHolder);
            AccountDTO accountFounded = accountService.createDTOFromAccountDomain(account);

            return new ResponseEntity<>(accountFounded, null, 200);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, 500);
        }
    }
}
