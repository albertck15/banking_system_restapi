package hu.csercsak_albert.banking_system.repository;

import hu.csercsak_albert.banking_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Mainly used for the account number generation to avoid duplicates
    boolean existsByAccountNumber(int accountNumber);

    boolean existsByUsername(String username);

    Optional<User> findByAccountNumber(int accountNumber);

}
