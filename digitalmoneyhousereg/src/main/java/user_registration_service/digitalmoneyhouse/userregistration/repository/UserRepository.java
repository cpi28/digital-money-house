package user_registration_service.digitalmoneyhouse.userregistration.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user_registration_service.digitalmoneyhouse.userregistration.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}

