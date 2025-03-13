package dashboard_service.digitalmoneyhousedash;


import dashboard_service.digitalmoneyhousedash.controller.DashboardController;
import dashboard_service.digitalmoneyhousedash.dto.AccountSummaryResponse;
import dashboard_service.digitalmoneyhousedash.dto.TransactionResponse;
import dashboard_service.digitalmoneyhousedash.exception.ResourceNotFoundException;
import dashboard_service.digitalmoneyhousedash.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();
    }

    @Test
    void getAccountSummary_ShouldReturn200_WhenSuccessful() throws Exception {
        AccountSummaryResponse response = new AccountSummaryResponse(1L, 15000.75);
        when(dashboardService.getAccountSummary(1L)).thenReturn(response);

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(1L))
                .andExpect(jsonPath("$.availableBalance").value(15000.75));

        verify(dashboardService, times(1)).getAccountSummary(1L);
    }

    @Test
    void getAccountSummary_ShouldReturn404_WhenAccountNotFound() throws Exception {
        when(dashboardService.getAccountSummary(1L)).thenThrow(new ResourceNotFoundException("Cuenta no encontrada"));


        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isNotFound());

        verify(dashboardService, times(1)).getAccountSummary(1L);
    }

    @Test
    void getLastTransactions_ShouldReturn200_WhenSuccessful() throws Exception {
        List<TransactionResponse> response = List.of(
                new TransactionResponse(1L, -500.00, LocalDateTime.now().minusDays(1), "Pago de servicios"),
                new TransactionResponse(2L, 2000.00, LocalDateTime.now().minusDays(2), "Depósito")
        );

        when(dashboardService.getLastFiveTransactions(1L)).thenReturn(response);

        mockMvc.perform(get("/api/accounts/1/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].amount").value(-500.00))
                .andExpect(jsonPath("$[1].amount").value(2000.00));

        verify(dashboardService, times(1)).getLastFiveTransactions(1L);
    }
}

