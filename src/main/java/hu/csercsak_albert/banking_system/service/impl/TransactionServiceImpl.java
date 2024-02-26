package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.dto.TransactionDto;
import hu.csercsak_albert.banking_system.entity.Balance;
import hu.csercsak_albert.banking_system.entity.Transaction;
import hu.csercsak_albert.banking_system.entity.User;
import hu.csercsak_albert.banking_system.exceptions.BalanceNotFoundException;
import hu.csercsak_albert.banking_system.exceptions.InvalidAmountException;
import hu.csercsak_albert.banking_system.exceptions.UserNotFoundException;
import hu.csercsak_albert.banking_system.mapper.TransactionMapper;
import hu.csercsak_albert.banking_system.repository.BalanceRepository;
import hu.csercsak_albert.banking_system.repository.TransactionRepository;
import hu.csercsak_albert.banking_system.repository.UserRepository;
import hu.csercsak_albert.banking_system.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(BalanceRepository balanceRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.balanceRepository = balanceRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    //*************************************************
    //  Transaction management
    //

    @Transactional
    @Override
    public TransactionDto deposit(TransactionDto transactionDto) {
        if (transactionDto.getAmount() <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        User from = userRepository.findByAccountNumber(transactionDto.getFromAccountNumber())
                .orElseThrow(() -> new UserNotFoundException("User not found with that account number(%d)".formatted(transactionDto.getFromAccountNumber())));
        Balance balance = balanceRepository.findByUserId(from.getId()).orElseThrow(() ->
                new BalanceNotFoundException("Balance not found with this User ID (%d)".formatted(from.getId())));
        double balanceAmount = balance.getBalance();
        balanceAmount += transactionDto.getAmount();
        balance.setBalance(balanceAmount);
        balanceRepository.save(balance);

        // TODO
        return null;
    }

    @Transactional
    @Override
    public TransactionDto withdraw(TransactionDto transactionDto) {
        User from = userRepository.findByAccountNumber(transactionDto.getFromAccountNumber())
                .orElseThrow(() -> new UserNotFoundException("User not found with that account number(%d)".formatted(transactionDto.getFromAccountNumber())));
        Balance balance = balanceRepository.findByUserId(from.getId())
                .orElseThrow(() -> new BalanceNotFoundException("Balance not found with this user ID(%d)".formatted(from.getId())));
        double balanceAmount = balance.getBalance();
        if (transactionDto.getAmount() > balanceAmount) {
            throw new InvalidAmountException("Amount can't be more than your balance");
        }
        balance.setBalance(balanceAmount - transactionDto.getAmount());
        balanceRepository.save(balance);

        // TODO
        return null;
    }

    @Transactional
    @Override
    public TransactionDto transfer(TransactionDto transactionDto) {
        User from = userRepository.findByAccountNumber(transactionDto.getFromAccountNumber())
                .orElseThrow(() -> new UserNotFoundException("User not found with that account number(%d)".formatted(transactionDto.getFromAccountNumber())));
        User to = userRepository.findByAccountNumber(transactionDto.getFromAccountNumber())
                .orElseThrow(() -> new UserNotFoundException("User not found with that account number(%d)".formatted(transactionDto.getFromAccountNumber())));
        Balance fromBalance = from.getBalance();
        Balance toBalance = to.getBalance();
        if (fromBalance.getBalance() < transactionDto.getAmount()) {
            throw new InvalidAmountException("Amount can't be more than your balance");
        }
        Double fromBalanceValue = fromBalance.getBalance() - transactionDto.getAmount();
        Double toBalanceValue = toBalance.getBalance() + transactionDto.getAmount();
        fromBalance.setBalance(fromBalanceValue);
        toBalance.setBalance(toBalanceValue);
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        transactionRepository.save(transaction);
        balanceRepository.saveAll(List.of(fromBalance, toBalance));

        // TODO
        return null;
    }

    //*************************************************
    //  Query methods
    //

    @Override
    public List<TransactionDto> getTransactionsById(Long id) {
        List<Transaction> listByFromId = transactionRepository.findByFromAccountId(id); //
        List<Transaction> listByToId = transactionRepository.findByToAccountId(id);
        return Stream.concat(listByFromId.stream(), listByToId.stream())
                .sorted()
                .map(TransactionMapper::mapToTransactionDto)
                .toList();
    }
}
