package hu.csercsak_albert.banking_system.service.impl;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.entity.Account;
import hu.csercsak_albert.banking_system.entity.User;
import hu.csercsak_albert.banking_system.enums.Role;
import hu.csercsak_albert.banking_system.exceptions.UserNotFoundException;
import hu.csercsak_albert.banking_system.mapper.UserMapper;
import hu.csercsak_albert.banking_system.repository.AccountRepository;
import hu.csercsak_albert.banking_system.repository.UserRepository;
import hu.csercsak_albert.banking_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation class for the UserService interface.
 * Provides functionality for creating, updating, deleting, and retrieving user information.
 * <p>
 * This class interacts with the UserRepository and AccountRepository to perform CRUD (Create, Read, Update, Delete)
 * operations on user and account data. It also uses the UserMapper class to map between DTOs (Data Transfer Objects)
 * and entity objects.
 * <p>
 * Transactional integrity is ensured by using Spring's @Transactional annotation on methods that perform database
 * operations. Authentication and authorization checks are handled to enforce security constraints, such as ensuring
 * that a user can only perform actions on their own account.
 * <p>
 * In addition to handling user-related operations, this class also provides methods for working with accounts,
 * including creating, updating, and deleting accounts associated with users.
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    public UserServiceImpl(UserRepository userRepository,
                           AccountRepository accountRepository,
                           AccountNumberGeneratorService accountNumberGenerator) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new user based on the provided user DTO.
     *
     * @param userDto The DTO representing the user to be created.
     * @return The HTTP status representing the success of the user creation operation (HttpStatus.CREATED).
     */
    @Transactional
    @Override
    public HttpStatus createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user = userRepository.save(user);
        return HttpStatus.CREATED;
    }

    /**
     * Updates an existing user with the specified ID using the details provided in the user DTO.
     *
     * @param id      The ID of the user to be updated.
     * @param userDto The DTO containing the updated user details.
     * @return The HTTP status representing the success of the user update operation (HttpStatus.ACCEPTED).
     * @throws UserNotFoundException if no user is found with the specified ID.
     */
    @Transactional
    @Override
    public HttpStatus updateUser(Long id, UserDto userDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with this ID(%d)".formatted(id)));

        if (isUserOwner(user)) {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setUsername(userDto.getUsername());
            user.setUpdatedAt(LocalDateTime.now());
            user = userRepository.save(user);
        }
        return HttpStatus.ACCEPTED;
    }

    /**
     * Deletes the user with the specified ID.
     *
     * @param id The ID of the user to be deleted.
     * @return The HTTP status representing the success of the user deletion operation (HttpStatus.NO_CONTENT).
     * @throws UserNotFoundException if no user is found with the specified ID.
     */
    @Transactional
    @Override
    public HttpStatus deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with that ID(%d)".formatted(id)));

        if (isUserOwner(user)) {
            Optional<List<Account>> accounts = accountRepository.findByUserId(user.getId());
            accounts.ifPresent(accountRepository::deleteAll);
            userRepository.delete(user);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.FORBIDDEN;
    }

    /**
     * Retrieves the user DTO corresponding to the specified user ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The DTO representing the user with the specified ID.
     * @throws UserNotFoundException if no user is found with the specified ID.
     */
    @Override
    public UserDto findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with the ID(%d)".formatted(id)));
    }

    /**
     * Retrieves the user DTO corresponding to the specified usesname.
     *
     * @param username The username of the user to be retrieved.
     * @return The DTO representing the user with the specified username.
     * @throws UserNotFoundException if no user is found with the specified username.
     */
    @Override
    public UserDto findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with the username(%s)".formatted(username)));
    }

    // Helper method
    private boolean isUserOwner(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return user.getUsername().equals(username);
    }
}
