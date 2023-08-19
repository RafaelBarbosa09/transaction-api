package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.application.DTO.SellerDTO;
import com.infnet.transactionapi.application.DTO.TransactionDTO;
import com.infnet.transactionapi.application.mappers.TransactionDTOMapper;
import com.infnet.transactionapi.domain.Exceptions.DuplicateTransactionException;
import com.infnet.transactionapi.domain.Exceptions.InactiveCardException;
import com.infnet.transactionapi.domain.Exceptions.LimitNotAvailableException;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import com.infnet.transactionapi.domain.repositories.AccountRepository;
import com.infnet.transactionapi.domain.repositories.SellerRepository;
import com.infnet.transactionapi.domain.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionDTOMapper mapper;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionDTO transactionDTO;
    private TransactionDomain transactionDomain;
    private AccountDomain accountDomain;
    private SellerDomain sellerDomain;

    @BeforeEach
    void setUp() {
        this.sellerDomain = new SellerDomain("Ifood");
        SellerDTO sellerDTO = new SellerDTO("Ifood");
        this.accountDomain = new AccountDomain("John Doe", new BigDecimal("1200"), true);
        AccountDTO accountDTO = new AccountDTO("John Doe", new BigDecimal("1200"), true);
        this.transactionDomain = new TransactionDomain(
            new BigDecimal("100"),
                new Date(2023 - 1900, Calendar.AUGUST, 18, 20, 0),
            1L,
            accountDomain,
            sellerDomain
        );
        this.transactionDTO = new TransactionDTO(
            new BigDecimal("100"),
                new Date(2023 - 1900, Calendar.AUGUST, 18, 20, 0),
            1L,
                accountDTO,
                sellerDTO
        );
    }

    @Test
    void testGetAllTransactions() {
        TransactionDomain transactionDomain2 = new TransactionDomain(
            new BigDecimal("200"),
            new Date(2023 - 1900, Calendar.AUGUST, 18, 20, 0),
            2L,
            new AccountDomain("Jane Smith", new BigDecimal("1500"), true),
            new SellerDomain("Uber Eats")
        );

        TransactionDTO transactionDTO2 = mapper.toDTO(transactionDomain2);

        List<TransactionDomain> transactionDomains = Arrays.asList(transactionDomain, transactionDomain2);
        List<TransactionDTO> expectedTransactionDTOs = Arrays.asList(transactionDTO, transactionDTO2);

        when(transactionRepository.findAll()).thenReturn(transactionDomains);
        when(mapper.toDTO(transactionDomain)).thenReturn(transactionDTO);
        when(mapper.toDTO(transactionDomain2)).thenReturn(transactionDTO2);

        List<TransactionDTO> actualTransactionDTOs = transactionService.getAllTransactions();
        assertEquals(expectedTransactionDTOs, actualTransactionDTOs);
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testGetTransactionById() {
        when(transactionRepository.findById(1L)).thenReturn(transactionDomain);
        when(mapper.toDTO(transactionDomain)).thenReturn(transactionDTO);

        TransactionDTO actualTransactionDTO = transactionService.getTransactionById(1L);
        assertEquals(transactionDTO, actualTransactionDTO);
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTransaction() {
        when(accountRepository.findByAccountHolder(accountDomain.getAccountHolder())).thenReturn(accountDomain);
        when(mapper.toDomain(transactionDTO)).thenReturn(transactionDomain);
        when(sellerRepository.findByName(sellerDomain.getName())).thenReturn(sellerDomain);
        when(transactionRepository.save(transactionDomain)).thenReturn(transactionDomain);
        when(mapper.toDTO(transactionDomain)).thenReturn(transactionDTO);

        TransactionDTO actualTransactionDTO = transactionService.createTransaction(transactionDTO);
        assertEquals(transactionDTO, actualTransactionDTO);
        verify(sellerRepository, times(1)).findByName(sellerDomain.getName());
        verify(transactionRepository, times(1)).save(transactionDomain);
    }

    @Test
    void testCreateTransactionFailureWithInvalidAccountHolder() {
        when(accountRepository.findByAccountHolder(accountDomain.getAccountHolder())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> transactionService.createTransaction(transactionDTO));

        verify(transactionRepository, times(0)).save(transactionDomain);
    }

    @Test
    void testCreateTransactionFailureHighFrequencySmallInterval() {
        TransactionDomain transactionDomain2 = transactionDomain;
        TransactionDomain transactionDomain3 = transactionDomain;

        when(accountRepository.findByAccountHolder(accountDomain.getAccountHolder())).thenReturn(accountDomain);
        when(mapper.toDomain(transactionDTO)).thenReturn(transactionDomain);
        when(sellerRepository.findByName(sellerDomain.getName())).thenReturn(sellerDomain);
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transactionDomain, transactionDomain2, transactionDomain3));

        assertThrows(RuntimeException.class, () -> transactionService.createTransaction(transactionDTO));

        verify(transactionRepository, times(0)).save(transactionDomain);
    }

    @Test
    void testCreateTransactionFailureHasDuplicateTransactions() {
        TransactionDomain transactionDomain2 = transactionDomain;

        when(accountRepository.findByAccountHolder(accountDomain.getAccountHolder())).thenReturn(accountDomain);
        when(mapper.toDomain(transactionDTO)).thenReturn(transactionDomain);
        when(sellerRepository.findByName(sellerDomain.getName())).thenReturn(sellerDomain);
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transactionDomain, transactionDomain2));

        assertThrows(DuplicateTransactionException.class, () -> transactionService.createTransaction(transactionDTO));

        verify(transactionRepository, times(0)).save(transactionDomain);
    }

    @Test
    void testCreateTransactionFailureWithInactiveCard() {
        AccountDomain invalidAccountDomain = new AccountDomain("John Doe", new BigDecimal("1200"), false);
        AccountDTO invalidAccountDTO = new AccountDTO("John Doe", new BigDecimal("1200"), false);
        transactionDomain.setAccount(invalidAccountDomain);
        transactionDTO.setAccount(invalidAccountDTO);

        when(accountRepository.findByAccountHolder(accountDomain.getAccountHolder())).thenReturn(invalidAccountDomain);
        when(mapper.toDomain(transactionDTO)).thenReturn(transactionDomain);
        when(sellerRepository.findByName(sellerDomain.getName())).thenReturn(sellerDomain);

        assertThrows(InactiveCardException.class, () -> transactionService.createTransaction(transactionDTO));

        verify(transactionRepository, times(0)).save(transactionDomain);
    }

    @Test
    void testCreateTransactionFailureWithUnavailableLimit() {
        transactionDomain.setValue(new BigDecimal("1201"));
        transactionDTO.setValue(new BigDecimal("1201"));

        when(accountRepository.findByAccountHolder(accountDomain.getAccountHolder())).thenReturn(accountDomain);
        when(mapper.toDomain(transactionDTO)).thenReturn(transactionDomain);
        when(sellerRepository.findByName(sellerDomain.getName())).thenReturn(sellerDomain);

        assertThrows(LimitNotAvailableException.class, () -> transactionService.createTransaction(transactionDTO));

        verify(transactionRepository, times(0)).save(transactionDomain);
    }
}