package hu.csercsak_albert.banking_system.repository;

import hu.csercsak_albert.banking_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountId(int fromAccountId);

    List<Transaction> findByToAccountId(int toAccountId);
}
