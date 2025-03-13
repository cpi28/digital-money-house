package dashboard_service.digitalmoneyhousedash.controller;


import dashboard_service.digitalmoneyhousedash.dto.AccountSummaryResponse;
import dashboard_service.digitalmoneyhousedash.dto.TransactionResponse;
import dashboard_service.digitalmoneyhousedash.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Obtiene el saldo disponible de una cuenta por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountSummaryResponse> getAccountSummary(@PathVariable Long id) {
        AccountSummaryResponse summary = dashboardService.getAccountSummary(id);
        return ResponseEntity.ok(summary);
    }

    /**
     * Obtiene los últimos 5 movimientos de una cuenta por su ID.
     */
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getLastTransactions(@PathVariable Long id) {
        List<TransactionResponse> transactions = dashboardService.getLastFiveTransactions(id);
        return ResponseEntity.ok(transactions);
    }
}

