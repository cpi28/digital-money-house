package dashboard_service.digitalmoneyhousedash.service;


import dashboard_service.digitalmoneyhousedash.dto.AccountSummaryResponse;
import dashboard_service.digitalmoneyhousedash.dto.TransactionResponse;
import dashboard_service.digitalmoneyhousedash.exception.ResourceNotFoundException;
import dashboard_service.digitalmoneyhousedash.model.Account;
import dashboard_service.digitalmoneyhousedash.model.Transaction;
import dashboard_service.digitalmoneyhousedash.repository.AccountRepository;
import dashboard_service.digitalmoneyhousedash.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public DashboardService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Obtiene el saldo disponible de una cuenta por su ID.
     */
    public AccountSummaryResponse getAccountSummary(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + accountId));

        return new AccountSummaryResponse(account.getId(), account.getBalance());
    }

    /**
     * Obtiene los últimos 5 movimientos de una cuenta.
     */
    public List<TransactionResponse> getLastFiveTransactions(Long accountId) {
        List<Transaction> transactions = transactionRepository.findTop5ByAccountIdOrderByDateDesc(accountId);

        return transactions.stream()
                .map(t -> new TransactionResponse(t.getId(), t.getAmount(), t.getDate(), t.getDescription()))
                .collect(Collectors.toList());
    }
}

