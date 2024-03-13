package hu.csercsak_albert.banking_system.repository;

import hu.csercsak_albert.banking_system.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(Long accountNumber);

    Optional<List<Account>> findByUserId(Long userId);

    boolean existsByAccountNumber(Long accountNumber);
}
