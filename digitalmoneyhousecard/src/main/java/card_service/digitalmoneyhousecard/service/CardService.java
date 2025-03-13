package card_service.digitalmoneyhousecard.service;


import card_service.digitalmoneyhousecard.dto.CardRequestDto;
import card_service.digitalmoneyhousecard.dto.CardResponseDto;

import java.util.List;

public interface CardService {
    List<CardResponseDto> getAllCardsByAccount(Long accountId);
    CardResponseDto getCardById(Long accountId, Long cardId);
    CardResponseDto createCard(Long accountId, CardRequestDto cardRequest);
    void deleteCard(Long accountId, Long cardId);
}
