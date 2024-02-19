package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.entity.User;
import hu.csercsak_albert.banking_system.exceptions.UserNotFoundException;
import hu.csercsak_albert.banking_system.mapper.UserMapper;
import hu.csercsak_albert.banking_system.repository.UserRepository;
import hu.csercsak_albert.banking_system.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Value("${account.number.min.value}")
    private int accNumberMin;

    @Value("${account.number.max.value}")
    private int accNumberMax;

    private final UserRepository userRepository;
//    private final PasswordEncoderService passwordEncoder;

    public UserServiceImpl(UserRepository userRepository /* PasswordEncoderService passwordEncoder*/) {
        this.userRepository = userRepository;
        /*this.passwordEncoder = passwordEncoder;*/
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        user.setAccountNumber(generateAccountNumber());
        //  user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return UserMapper.mapToUserDto(userRepository.save(user));
    }

    @Override
    public HttpStatus deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public UserDto findByAccountNumber(int accountNumber) {
        Optional<User> userOptional = userRepository.findByAccountNumber(accountNumber);
        return userOptional.map(UserMapper::mapToUserDto) //
                .orElseThrow(() -> new UserNotFoundException("User not found with account number(%d)".formatted(accountNumber)));
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserMapper::mapToUserDto)//
                .orElseThrow(() -> new UserNotFoundException("User not found with the ID(%d)".formatted(id)));
    }

    @Override
    public boolean isExistByAccountNumber(int accountNumber) {
        Optional<User> userOptional = userRepository.findByAccountNumber(accountNumber);
        return userOptional.isPresent();
    }

    //************************************
    // Helper methods
    //
    private Integer generateAccountNumber() {
        Random random = new Random();
        Integer accNumber = -1;
        do {
            accNumber = random.nextInt(accNumberMax - accNumberMin + 1) + accNumberMin;
        } while (userRepository.existsByAccountNumber(accNumber));
        return accNumber;
    }


}
