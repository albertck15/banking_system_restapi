package hu.csercsak_albert.banking_system.repository;

import hu.csercsak_albert.banking_system.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByUserId(Long userId);
}
