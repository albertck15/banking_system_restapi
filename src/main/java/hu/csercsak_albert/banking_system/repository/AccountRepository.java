package hu.csercsak_albert.banking_system.repository;

import hu.csercsak_albert.banking_system.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(Long accountNumber);

    Optional<List<Account>> findByUserId(Long userId);

    boolean existsByAccountNumber(Long accountNumber);

}
