package hu.csercsak_albert.banking_system.mapper;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.entity.Balance;
import hu.csercsak_albert.banking_system.entity.User;

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
                .dateOfBirth(userDto.getDateOfBirth())
                .createdAtDate(userDto.getCreatedAtDate())
                .updatedAtDate(userDto.getUpdatedAtDate())
                .lastLoginDate(userDto.getLastLoginDate())
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
                .dateOfBirth(user.getDateOfBirth())
                .createdAtDate(user.getCreatedAtDate())
                .updatedAtDate(user.getUpdatedAtDate())
                .lastLoginDate(user.getLastLoginDate())
                .balanceDto(BalanceMapper.mapToBalanceDto(user.getBalance()))
                .build();
    }
}
