package card_service.digitalmoneyhousecard.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cards")
@Data
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;
    @Column(name = "card_type", nullable = false)
    private String cardType; // "CREDIT" o "DEBIT"
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    @Column(name = "active", nullable = false)
    private boolean active;

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Card(Long id, String cardNumber, String cardType, Long accountId, boolean active) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.accountId = accountId;
        this.active = active;
    }

    public Card() {
    }
}
