package card_service.digitalmoneyhousecard.dto;


import lombok.Data;

@Data
public class CardResponseDto {
    private Long id;
    private String cardNumber;
    private String cardType;
    private Long accountId;

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
}
