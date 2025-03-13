package card_service.digitalmoneyhousecard.repository;


import card_service.digitalmoneyhousecard.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByAccountId(Long accountId);
    boolean existsByCardNumber(String cardNumber);
}
