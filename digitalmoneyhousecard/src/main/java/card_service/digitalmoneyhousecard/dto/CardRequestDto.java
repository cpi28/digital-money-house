package card_service.digitalmoneyhousecard.dto;


import lombok.Data;

@Data
public class CardRequestDto {
    private String cardNumber;
    private String cardType;

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
