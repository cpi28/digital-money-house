package card_service.digitalmoneyhousecard.exception;


public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String message) {
        super(message);
    }
}
