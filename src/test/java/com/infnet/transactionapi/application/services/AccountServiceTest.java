package com.infnet.transactionapi.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.application.mappers.AccountDTOMapper;
import com.infnet.transactionapi.domain.Exceptions.AccountAlreadyExistsException;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountDTOMapper mapper;

    @InjectMocks
    private AccountService accountService;

    private AccountDTO accountDTO;
    private AccountDomain accountDomain;
    private AccountDomain account;

    @BeforeEach
    public void setUp() {
        this.accountDTO = new AccountDTO("John Doe", null, null);
        this.accountDomain = new AccountDomain("John Doe", null, null);
        this.account = new AccountDomain("John Doe", new BigDecimal("1200"), true);
    }

    @Test
    public void testCreateAccountSuccess() {
        when(mapper.toDomain(any(AccountDTO.class))).thenReturn(accountDomain);
        when(accountService.createAccountDomainFromDTO(accountDTO)).thenReturn(accountDomain);
        when(accountRepository.save(any(AccountDomain.class))).thenReturn(account);

        accountDTO.setAvailableLimit(account.getAvailableLimit());
        accountDTO.setActiveCard(account.getActiveCard());
        when(accountService.createDTOFromAccountDomain(account)).thenReturn(accountDTO);

        AccountDTO result = accountService.createAccount(accountDTO);

        assertEquals(accountDTO, result);
        verify(accountRepository, times(1)).save(any(AccountDomain.class));
    }

    @Test
    public void testCreateAccountFailureAccountAlreadyExists() {
        AccountService spyAccountService = spy(accountService);

        when(mapper.toDomain(any(AccountDTO.class))).thenReturn(accountDomain);
        when(accountService.createAccountDomainFromDTO(accountDTO)).thenReturn(accountDomain);

        doReturn(true).when(spyAccountService).accountAlreadyExists(accountDomain);

        assertThrows(AccountAlreadyExistsException.class, () -> spyAccountService.createAccount(accountDTO));
    }
}
