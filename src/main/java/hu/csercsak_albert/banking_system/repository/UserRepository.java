package hu.csercsak_albert.banking_system.repository;

import hu.csercsak_albert.banking_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Mainly used for the account number generation to avoid duplicates
    boolean existsByAccountNumber(int accountNumber);

    Optional<User> findByAccountNumber(int accountNumber);

}
