package hu.csercsak_albert.banking_system.repository;

import hu.csercsak_albert.banking_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountNumber(long fromAccountNumber);

    List<Transaction> findByToAccountNumber(long toAccountNumber);
}
