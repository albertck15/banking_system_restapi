package hu.csercsak_albert.banking_system.mapper;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto){
        return new User(userDto.getId(), //
                userDto.getAccountNumber(), //
                userDto.getUsername(), //
                userDto.getPassword(), //
                userDto.getEmail(), //
                userDto.getFirstName(), //
                userDto.getLastName(), //
                userDto.getDateOfBirth(), //
                userDto.getCreatedAtDate(), //
                userDto.getUpdatedAtDate(), //
                userDto.getLastLoginDate());
    }

    public static UserDto mapToUserDto(User user){
        return new UserDto(user.getId(), //
                user.getAccountNumber(), //
                user.getUsername(), //
                user.getPassword(), //
                user.getEmail(), //
                user.getFirstName(), //
                user.getLastName(), //
                user.getDateOfBirth(), //
                user.getCreatedAtDate(), //
                user.getUpdatedAtDate(), //
                user.getLastLoginDate());
    }
}
