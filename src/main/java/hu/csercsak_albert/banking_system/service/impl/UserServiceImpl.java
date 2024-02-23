package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.dto.BalanceDto;
import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.entity.Balance;
import hu.csercsak_albert.banking_system.entity.User;
import hu.csercsak_albert.banking_system.exceptions.BalanceNotFoundException;
import hu.csercsak_albert.banking_system.exceptions.InvalidAmountException;
import hu.csercsak_albert.banking_system.exceptions.UserNotFoundException;
import hu.csercsak_albert.banking_system.mapper.BalanceMapper;
import hu.csercsak_albert.banking_system.mapper.UserMapper;
import hu.csercsak_albert.banking_system.repository.BalanceRepository;
import hu.csercsak_albert.banking_system.repository.UserRepository;
import hu.csercsak_albert.banking_system.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Value("${account.number.min.value}")
    private int accNumberMin;

    @Value("${account.number.max.value}")
    private int accNumberMax;

    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
//    private final PasswordEncoderService passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BalanceRepository balanceRepository /* PasswordEncoderService passwordEncoder*/) {
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
        /*this.passwordEncoder = passwordEncoder;*/
    }


    @Transactional
    @Override
    public UserDto createUser(UserDto userDto, Double initialBalance) {
        if (initialBalance == null) {
            initialBalance = 0.0d;
        }
        if (initialBalance < 0) {
            throw new InvalidAmountException("Initial balance must be 0 or positive");
        }
        BalanceDto balanceDto = new BalanceDto();
        balanceDto.setBalance(initialBalance);
        userDto.setBalanceDto(balanceDto);
        User user = UserMapper.mapToUser(userDto);
        user.setAccountNumber(generateAccountNumber());
        Balance balance = user.getBalance();
        balanceRepository.save(balance);
        //  user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = userRepository.save(user);
        return UserMapper.mapToUserDto(user);
    }

    @Transactional
    @Override
    public HttpStatus deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<Balance> balanceOptional = balanceRepository.findByUserId(id);
        if (userOptional.isPresent() && balanceOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            balanceRepository.delete(balanceOptional.get());
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public UserDto findByAccountNumber(int accountNumber) {
        Optional<User> userOptional = userRepository.findByAccountNumber(accountNumber);
        UserDto userDto = userOptional.map(UserMapper::mapToUserDto) //
                .orElseThrow(() -> new UserNotFoundException("User not found with account number(%d)".formatted(accountNumber)));
        Optional<Balance> balanceOptional = balanceRepository.findByUserId(userDto.getId());
        if (balanceOptional.isPresent()) {
            Balance balance = balanceOptional.get();
            userDto.setBalanceDto(BalanceMapper.mapToBalanceDto(balance));
            return userDto;
        }
        throw new UserNotFoundException("Balance not found for this account with the number(%d)".formatted(accountNumber));
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        UserDto userDto = userOptional.map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with the ID(%d)".formatted(id)));
        Optional<Balance> balance = balanceRepository.findByUserId(id);
        BalanceDto balanceDto = balance.map(BalanceMapper::mapToBalanceDto)
                .orElseThrow(() -> new BalanceNotFoundException("Balance not found with this user ID(%d)".formatted(id)));
        userDto.setBalanceDto(balanceDto);
        return userDto;
    }

    @Override
    public boolean isExistByAccountNumber(int accountNumber) {
        Optional<User> userOptional = userRepository.findByAccountNumber(accountNumber);
        return userOptional.isPresent();
    }

    @Transactional
    @Override
    public HttpStatus deposit(Long id, double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        Optional<Balance> balanceOptional = balanceRepository.findByUserId(id);
        Balance balance = balanceOptional.orElseThrow(() -> new BalanceNotFoundException("Balance not found with this User ID (%d)".formatted(id)));
        double balanceAmount = balance.getBalance();
        balanceAmount += amount;
        balance.setBalance(balanceAmount);
        balanceRepository.save(balance);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus withdraw(Long id, double amount) {
        Optional<Balance> balanceOptional = balanceRepository.findByUserId(id);
        Balance balance = balanceOptional.orElseThrow(() -> new BalanceNotFoundException("Balance not found with this user ID(%d)".formatted(id)));
        double balanceAmount = balance.getBalance();
        if (amount > balanceAmount) {
            throw new InvalidAmountException("Withdraw amount can't be more than the balance");
        }
        balance.setBalance(balanceAmount - amount);
        balanceRepository.save(balance);
        return HttpStatus.OK;
    }

    //************************************
    // Helper methods
    //
    private Integer generateAccountNumber() {
        Random random = new Random();
        int accNumber = -1;
        do {
            accNumber = random.nextInt(accNumberMax - accNumberMin + 1) + accNumberMin;
        } while (userRepository.existsByAccountNumber(accNumber));
        return accNumber;
    }

}
