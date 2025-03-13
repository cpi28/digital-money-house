package dashboard_service.digitalmoneyhousedash.dto;


/**
 * DTO para devolver el saldo disponible de la cuenta.
 */
public class AccountSummaryResponse {
    private Long accountId;
    private double availableBalance;

    public AccountSummaryResponse(Long accountId, double availableBalance) {
        this.accountId = accountId;
        this.availableBalance = availableBalance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }
}

