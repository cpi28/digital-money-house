package profile_service.digitalmoneyhouseprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import profile_service.digitalmoneyhouseprofile.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
