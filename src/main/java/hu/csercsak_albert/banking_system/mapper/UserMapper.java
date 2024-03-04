package hu.csercsak_albert.banking_system.mapper;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.entity.Balance;
import hu.csercsak_albert.banking_system.entity.User;

import java.time.LocalDate;

public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .accountNumber(userDto.getAccountNumber())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .dateOfBirth(LocalDate.parse(userDto.getDateOfBirth()))
                .createdAt(userDto.getCreatedAt())
                .updatedAt(userDto.getUpdatedAt())
                .lastLogin(userDto.getLastLogin())
                .build();

        // Setting up Bi-directional relationship between balance and user objects
        Balance balance = BalanceMapper.mapToBalance(userDto.getBalanceDto());
        balance.setUser(user);
        user.setBalance(balance);

        return user;
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .accountNumber(user.getAccountNumber())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth().toString())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLogin(user.getLastLogin())
                .balanceDto(BalanceMapper.mapToBalanceDto(user.getBalance()))
                .build();
    }
}
