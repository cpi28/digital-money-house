package dashboard_service.digitalmoneyhousedash.dto;


import java.time.LocalDateTime;

/**
 * DTO para representar una transacción.
 */
public class TransactionResponse {
    private Long transactionId;
    private double amount;
    private LocalDateTime date;
    private String description;

    public TransactionResponse(Long transactionId, double amount, LocalDateTime date, String description) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

