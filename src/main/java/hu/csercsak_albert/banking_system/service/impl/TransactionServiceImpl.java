package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.dto.TransactionDto;
import hu.csercsak_albert.banking_system.entity.Account;
import hu.csercsak_albert.banking_system.entity.Transaction;
import hu.csercsak_albert.banking_system.entity.User;
import hu.csercsak_albert.banking_system.exceptions.AccountNotFoundException;
import hu.csercsak_albert.banking_system.exceptions.InvalidAmountException;
import hu.csercsak_albert.banking_system.exceptions.UserNotFoundException;
import hu.csercsak_albert.banking_system.mapper.TransactionMapper;
import hu.csercsak_albert.banking_system.repository.AccountRepository;
import hu.csercsak_albert.banking_system.repository.TransactionRepository;
import hu.csercsak_albert.banking_system.repository.UserRepository;
import hu.csercsak_albert.banking_system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Provides transaction management services including deposit, withdrawal, and transfer operations.
 * <p>
 * This service class handles various types of transactions such as deposit, withdrawal, and transfer.
 * It interacts with the UserRepository, AccountRepository, and TransactionRepository to perform database
 * operations related to user accounts and transactions.
 * <p>
 * Transactional integrity is ensured by annotating methods with Spring's @Transactional annotation,
 * guaranteeing that transactions are atomic and consistent across multiple database operations.
 * <p>
 * Security measures are implemented to handle exceptions such as AccountNotFoundException and
 * InvalidAmountException, ensuring that transactions are performed securely and within constraints.
 * <p>
 * Additionally, this service provides query methods to retrieve transaction history for a given account number.
 * The retrieved transactions include both inbound and outbound transactions associated with the account.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    //*************************************************
    //  Transaction management
    //

    /**
     * Making a deposit
     *
     * @param transactionDto Representing the incoming payload in a class
     * @return A class representing the transaction.
     * @throws AccountNotFoundException if the account number is invalid
     */
    @Transactional
    @Override
    public TransactionDto deposit(TransactionDto transactionDto) {

        // Validating amount
        if (transactionDto.getAmount() <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }

        // Getting account from repo
        Account account = accountRepository.findByAccountNumber(transactionDto.getToAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with that account number(%d)"
                        .formatted(transactionDto.getToAccountNumber())));

        // Making the "deposit"
        double balance = account.getBalance();
        balance += transactionDto.getAmount();
        account.setBalance(balance);

        // Saving and logging transaction into database
        accountRepository.save(account);
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        transaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(transaction);
    }

    /**
     * Making a withdrawal
     *
     * @param transactionDto Representing the incoming payload in a class
     * @return A class representing the transaction.
     * @throws AccountNotFoundException if the account number is invalid
     */
    @Transactional
    @Override
    public TransactionDto withdraw(TransactionDto transactionDto) {

        // Getting account from repo
        Account account = accountRepository.findByAccountNumber(transactionDto.getFromAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with this account number(%d)"
                        .formatted(transactionDto.getFromAccountNumber())));

        // Validating if the withdrawal can be made
        double balance = account.getBalance();
        if (transactionDto.getAmount() > balance) {
            throw new InvalidAmountException("Amount can't be more than your balance");
        }

        //Making the "withdrawal"
        account.setBalance(balance - transactionDto.getAmount());

        //Saving and logging into db
        accountRepository.save(account);
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        transaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(transactionRepository.save(transaction));
    }

    /**
     * Making a transfer
     *
     * @param transactionDto Representing the incoming payload in a class where we must include the receiver's account number too
     * @return A class representing the transaction.
     * @throws InvalidAmountException   if the amount is more than the sender's balance
     * @throws AccountNotFoundException if the sender or the receiver's account number is invalid
     */
    @Transactional
    @Override
    public TransactionDto transfer(TransactionDto transactionDto) {

        // Validating receiver's account number
        if (transactionDto.getToAccountNumber() == 0) {
            throw new AccountNotFoundException("Must enter the receiver's account number for a transfer");
        }
        if (!accountRepository.existsByAccountNumber(transactionDto.getFromAccountNumber())
                || !accountRepository.existsByAccountNumber(transactionDto.getToAccountNumber())) {
            throw new AccountNotFoundException("Invalid sender or receiver account number");
        }

        // Getting from and to accounts
        Account from = accountRepository.findByAccountNumber(transactionDto.getFromAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with this account number(%d)"
                        .formatted(transactionDto.getFromAccountNumber())));
        Account to = accountRepository.findByAccountNumber(transactionDto.getToAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with this account number(%d)"
                        .formatted(transactionDto.getFromAccountNumber())));

        // Validating amount
        if (from.getBalance() < transactionDto.getAmount()) {
            throw new InvalidAmountException("Amount can't be more than your balance");
        }

        // Saving and logging into db
        Double fromBalanceValue = from.getBalance() - transactionDto.getAmount();
        Double toBalanceValue = to.getBalance() + transactionDto.getAmount();
        from.setBalance(fromBalanceValue);
        to.setBalance(toBalanceValue);
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        transaction = transactionRepository.save(transaction);
        accountRepository.saveAll(List.of(from, to));

        return TransactionMapper.mapToTransactionDto(transaction);
    }

    //*************************************************
    //  Query methods
    //

    /**
     * Method to query transactions made or received by an account number
     *
     * @return list of transactions that made or received by this account number
     */
    @Override
    public List<TransactionDto> getTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with the username(%s)".formatted(username)));

        List<Account> accounts = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AccountNotFoundException("There is no account for the user with the id(%d)".formatted(user.getId())));

        List<Transaction> listByFromId = new ArrayList<>();
        List<Transaction> listByToId = new ArrayList<>();

        accounts.forEach(account -> {
            Long accountNumber = account.getAccountNumber();
            listByFromId.addAll(transactionRepository.findByFromAccountNumber(accountNumber));
            listByToId.addAll(transactionRepository.findByToAccountNumber(accountNumber));
        });

        return Stream.concat(listByFromId.stream(), listByToId.stream())
                .sorted()
                .map(TransactionMapper::mapToTransactionDto)
                .toList();
    }
}
