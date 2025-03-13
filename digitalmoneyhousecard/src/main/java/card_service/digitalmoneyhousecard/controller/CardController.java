package card_service.digitalmoneyhousecard.controller;


import card_service.digitalmoneyhousecard.dto.CardRequestDto;
import card_service.digitalmoneyhousecard.dto.CardResponseDto;
import card_service.digitalmoneyhousecard.exception.CardAlreadyExistsException;
import card_service.digitalmoneyhousecard.exception.CardNotFoundException;
import card_service.digitalmoneyhousecard.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<CardResponseDto>> getAllCards(@PathVariable Long accountId) {
        List<CardResponseDto> cards = cardService.getAllCardsByAccount(accountId);
        return ResponseEntity.ok(cards); // Status 200 incluso si está vacío
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> getCardById(@PathVariable Long accountId,
                                                       @PathVariable Long cardId) {
        try {
            CardResponseDto card = cardService.getCardById(accountId, cardId);
            return ResponseEntity.ok(card);
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<CardResponseDto> createCard(@PathVariable Long accountId,
                                                      @RequestBody CardRequestDto cardRequest) {
        try {
            CardResponseDto card = cardService.createCard(accountId, cardRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(card);
        } catch (CardAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long accountId,
                                           @PathVariable Long cardId) {
        try {
            cardService.deleteCard(accountId, cardId);
            return ResponseEntity.ok().build();
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleGenericException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
