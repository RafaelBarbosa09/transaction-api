package com.infnet.transactionapi.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.infnet.transactionapi.application.dto.AccountDTO;
import com.infnet.transactionapi.application.mappers.AccountDTOMapper;
import com.infnet.transactionapi.domain.exceptions.AccountAlreadyExistsException;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountDTOMapper mapper;

    @InjectMocks
    private AccountService accountService;

    private AccountDTO accountDTO;
    private AccountDomain accountDomain;
    private AccountDomain account;
    private String accountHolder;

    @BeforeEach
    public void setUp() {
        this.accountDTO = new AccountDTO("John Doe", null, null);
        this.accountDomain = new AccountDomain("John Doe", null, null);
        this.account = new AccountDomain("John Doe", new BigDecimal("1200"), true);
        this.accountHolder = "John Doe";
    }

    @Test
    void testGetAllAccounts() {
        AccountDomain accountDomain2 = new AccountDomain("Jane Smith", new BigDecimal("1500"), true);
        AccountDTO accountDTO2 = new AccountDTO("Jane Smith", new BigDecimal("1500"), true);

        List<AccountDomain> accountDomains = Arrays.asList(accountDomain, accountDomain2);
        List<AccountDTO> expectedAccountDTOs = Arrays.asList(accountDTO, accountDTO2);

        when(accountRepository.findAll()).thenReturn(accountDomains);
        when(mapper.toDTO(accountDomain)).thenReturn(accountDTO);
        when(mapper.toDTO(accountDomain2)).thenReturn(accountDTO2);

        List<AccountDTO> result = accountService.getAllAccounts();

        assertEquals(expectedAccountDTOs, result);

        verify(accountRepository, times(1)).findAll();
        verify(mapper, times(1)).toDTO(accountDomain);
        verify(mapper, times(1)).toDTO(accountDomain2);
    }

    @Test
    void testCreateAccountSuccess() {
        when(mapper.toDomain(accountDTO)).thenReturn(accountDomain);
        when(accountRepository.save(any(AccountDomain.class))).thenReturn(account);
        when(mapper.toDTO(account)).thenReturn(accountDTO);

        AccountDTO result = accountService.createAccount(accountDTO);

        assertEquals(accountDTO, result);
        verify(accountRepository, times(1)).save(any(AccountDomain.class));
    }

    @Test
    void testCreateAccountFailureAccountAlreadyExists() {
        AccountService spyAccountService = spy(accountService);

        when(mapper.toDomain(accountDTO)).thenReturn(accountDomain);

        doReturn(true).when(spyAccountService).accountAlreadyExists(accountDomain);

        assertThrows(AccountAlreadyExistsException.class, () -> spyAccountService.createAccount(accountDTO));
        verify(accountRepository, times(0)).save(any(AccountDomain.class));
    }

    @Test
    void testAccountAlreadyExists() {
        when(accountRepository.findByAccountHolder(accountHolder)).thenReturn(accountDomain);

        Boolean result = accountService.accountAlreadyExists(accountDomain);

        assertEquals(true, result);
        verify(accountRepository, times(1)).findByAccountHolder(accountHolder);
    }

    @Test
    void testGetAccountById() {
        Long id = 1L;
        when(accountRepository.findById(id)).thenReturn(account);
        when(mapper.toDTO(account)).thenReturn(accountDTO);

        AccountDTO result = accountService.getAccountById(id);

        assertEquals(accountDTO, result);
        verify(accountRepository, times(1)).findById(id);
    }

    @Test
    void testGetAccountByIdFailure() {
        Long id = 1L;
        when(accountRepository.findById(id)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> accountService.getAccountById(id));
        verify(accountRepository, times(1)).findById(id);
    }

    @Test
    void testGetAccountByAccountHolder() {
        when(accountRepository.findByAccountHolder(accountHolder)).thenReturn(account);
        when(mapper.toDTO(account)).thenReturn(accountDTO);

        AccountDTO result = accountService.getAccountByAccountHolder(accountHolder);

        assertEquals(accountDTO, result);
        verify(accountRepository, times(1)).findByAccountHolder(accountHolder);
    }

    @Test
    void testGetAccountByAccountHolderFailure() {
        when(accountRepository.findByAccountHolder(accountHolder)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> accountService.getAccountByAccountHolder(accountHolder));
        verify(accountRepository, times(1)).findByAccountHolder(accountHolder);
    }
}
