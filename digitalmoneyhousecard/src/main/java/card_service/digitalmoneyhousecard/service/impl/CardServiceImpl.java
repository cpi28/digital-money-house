package card_service.digitalmoneyhousecard.service.impl;


import card_service.digitalmoneyhousecard.dto.CardRequestDto;
import card_service.digitalmoneyhousecard.dto.CardResponseDto;
import card_service.digitalmoneyhousecard.exception.CardAlreadyExistsException;
import card_service.digitalmoneyhousecard.exception.CardNotFoundException;
import card_service.digitalmoneyhousecard.model.Card;
import card_service.digitalmoneyhousecard.repository.CardRepository;
import card_service.digitalmoneyhousecard.service.CardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<CardResponseDto> getAllCardsByAccount(Long accountId) {
        List<Card> cards = cardRepository.findByAccountId(accountId);
        return cards.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public CardResponseDto getCardById(Long accountId, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .filter(c -> c.getAccountId().equals(accountId))
                .orElseThrow(() -> new CardNotFoundException("No se encuentra la tarjeta para la cuenta"));
        return mapToDto(card);
    }

    @Override
    public CardResponseDto createCard(Long accountId, CardRequestDto cardRequest) {
        if (cardRepository.existsByCardNumber(cardRequest.getCardNumber())) {
            throw new CardAlreadyExistsException("La tarjeta esta asociada a otra cuenta");
        }

        Card card = new Card();
        card.setCardNumber(cardRequest.getCardNumber());
        card.setCardType(cardRequest.getCardType());
        card.setAccountId(accountId);
        card.setActive(true);

        Card savedCard = cardRepository.save(card);
        return mapToDto(savedCard);
    }

    @Override
    public void deleteCard(Long accountId, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .filter(c -> c.getAccountId().equals(accountId))
                .orElseThrow(() -> new CardNotFoundException("No se encuentra la tarjeta para la cuenta"));
        cardRepository.delete(card);
    }

    private CardResponseDto mapToDto(Card card) {
        CardResponseDto dto = new CardResponseDto();
        dto.setId(card.getId());
        dto.setCardNumber(card.getCardNumber());
        dto.setCardType(card.getCardType());
        dto.setAccountId(card.getAccountId());
        return dto;
    }
}
