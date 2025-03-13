package dashboard_service.digitalmoneyhousedash.repository;



import dashboard_service.digitalmoneyhousedash.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTop5ByAccountIdOrderByDateDesc(Long accountId);
}

