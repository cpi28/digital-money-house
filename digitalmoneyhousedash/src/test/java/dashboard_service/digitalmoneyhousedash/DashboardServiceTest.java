package dashboard_service.digitalmoneyhousedash;


import dashboard_service.digitalmoneyhousedash.dto.AccountSummaryResponse;
import dashboard_service.digitalmoneyhousedash.dto.TransactionResponse;
import dashboard_service.digitalmoneyhousedash.exception.ResourceNotFoundException;
import dashboard_service.digitalmoneyhousedash.model.Account;
import dashboard_service.digitalmoneyhousedash.model.Transaction;
import dashboard_service.digitalmoneyhousedash.repository.AccountRepository;
import dashboard_service.digitalmoneyhousedash.repository.TransactionRepository;
import dashboard_service.digitalmoneyhousedash.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private DashboardService dashboardService;

    private Account mockAccount;
    private List<Transaction> mockTransactions;

    @BeforeEach
    void setUp() {
        mockAccount = new Account(1L, 15000.75);

        mockTransactions = List.of(
                new Transaction(1L, -500.00, LocalDateTime.now().minusDays(1), "Pago de servicios"),
                new Transaction(2L, 2000.00, LocalDateTime.now().minusDays(2), "Depósito"),
                new Transaction(3L, -150.00, LocalDateTime.now().minusDays(3), "Compra en supermercado")
        );
    }

    @Test
    void getAccountSummary_ShouldReturnAccountSummary_WhenAccountExists() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(mockAccount));

        AccountSummaryResponse response = dashboardService.getAccountSummary(1L);

        assertNotNull(response);
        assertEquals(1L, response.getAccountId());
        assertEquals(15000.75, response.getAvailableBalance());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void getAccountSummary_ShouldThrowException_WhenAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> dashboardService.getAccountSummary(1L));
    }

    @Test
    void getLastFiveTransactions_ShouldReturnTransactions_WhenAccountExists() {
        when(transactionRepository.findTop5ByAccountIdOrderByDateDesc(1L)).thenReturn(mockTransactions);

        List<TransactionResponse> response = dashboardService.getLastFiveTransactions(1L);

        assertNotNull(response);
        assertEquals(3, response.size()); // Solo se generaron 3 transacciones en el mock
        verify(transactionRepository, times(1)).findTop5ByAccountIdOrderByDateDesc(1L);
    }
}

