package dashboard_service.digitalmoneyhousedash;


import dashboard_service.digitalmoneyhousedash.dto.AccountSummaryResponse;
import dashboard_service.digitalmoneyhousedash.dto.TransactionResponse;
import dashboard_service.digitalmoneyhousedash.model.Account;
import dashboard_service.digitalmoneyhousedash.model.Transaction;
import dashboard_service.digitalmoneyhousedash.repository.AccountRepository;
import dashboard_service.digitalmoneyhousedash.repository.TransactionRepository;
import dashboard_service.digitalmoneyhousedash.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class DashboardServiceIT {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private DashboardService dashboardService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        dashboardService = new DashboardService(accountRepository, transactionRepository);

        testAccount = new Account();
        testAccount.setBalance(10000.00);
        accountRepository.save(testAccount);

        transactionRepository.saveAll(List.of(
                new Transaction(testAccount.getId(), -200.00, LocalDateTime.now().minusDays(1), "Pago de servicios"),
                new Transaction(testAccount.getId(), 500.00, LocalDateTime.now().minusDays(2), "Depósito"),
                new Transaction(testAccount.getId(), -100.00, LocalDateTime.now().minusDays(3), "Compra en supermercado")
        ));
    }

    @Test
    void getAccountSummary_ShouldReturnAccountDetails_WhenAccountExists() {
        AccountSummaryResponse response = dashboardService.getAccountSummary(testAccount.getId());

        assertThat(response).isNotNull();
        assertThat(response.getAccountId()).isEqualTo(testAccount.getId());
        assertThat(response.getAvailableBalance()).isEqualTo(10000.00);
    }

    @Test
    void getAccountSummary_ShouldThrowException_WhenAccountNotFound() {
        assertThrows(RuntimeException.class, () -> dashboardService.getAccountSummary(999L));
    }

    @Test
    void getLastFiveTransactions_ShouldReturnRecentTransactions_WhenAccountExists() {
        List<TransactionResponse> response = dashboardService.getLastFiveTransactions(testAccount.getId());

        assertThat(response).isNotEmpty();
        assertThat(response.size()).isEqualTo(3); // Hay 3 transacciones en la base de datos de prueba
    }
}

