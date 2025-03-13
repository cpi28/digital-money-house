package dashboard_service.digitalmoneyhousedash;


import dashboard_service.digitalmoneyhousedash.model.Account;
import dashboard_service.digitalmoneyhousedash.model.Transaction;
import dashboard_service.digitalmoneyhousedash.repository.AccountRepository;
import dashboard_service.digitalmoneyhousedash.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DashboardControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();

        testAccount = new Account();
        testAccount.setBalance(5000.00);
        accountRepository.save(testAccount);

        transactionRepository.saveAll(List.of(
                new Transaction(testAccount.getId(), -100.00, LocalDateTime.now().minusDays(1), "Pago de servicios"),
                new Transaction(testAccount.getId(), 300.00, LocalDateTime.now().minusDays(2), "Depósito")
        ));
    }

    @Test
    void getAccountSummary_ShouldReturn200_WhenSuccessful() throws Exception {
        mockMvc.perform(get("/api/accounts/" + testAccount.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(testAccount.getId()))
                .andExpect(jsonPath("$.availableBalance").value(5000.00));
    }

    @Test
    void getAccountSummary_ShouldReturn404_WhenAccountNotFound() throws Exception {
        mockMvc.perform(get("/api/accounts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getLastTransactions_ShouldReturn200_WhenSuccessful() throws Exception {
        mockMvc.perform(get("/api/accounts/" + testAccount.getId() + "/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].amount").value(-100.00))
                .andExpect(jsonPath("$[1].amount").value(300.00));
    }
}

