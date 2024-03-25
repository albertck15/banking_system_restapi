package hu.csercsak_albert.banking_system.controller;

import hu.csercsak_albert.banking_system.dto.UserDto;
import hu.csercsak_albert.banking_system.enums.Role;
import hu.csercsak_albert.banking_system.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller class for handling user-related operations.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieve a user by ID.
     *
     * @param id          The ID of the user to retrieve.
     * @param userDetails The UserDetails of the authenticated user.
     * @return ResponseEntity containing the user details if the requester has appropriate permissions.
     * @throws AccessDeniedException if the requester does not have the right permissions.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getAccountById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        UserDto userDto = userService.findById(id);
        if (isOwnerOrAdmin(userDetails, id)) {
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
        throw new AccessDeniedException("You do not have the right permissions");
    }

    /**
     * Update a user.
     *
     * @param userDto     The updated user details.
     * @param id          The ID of the user to update.
     * @param userDetails The UserDetails of the authenticated user.
     * @return ResponseEntity containing the updated user details.
     * @throws AccessDeniedException if the requester does not have the right permissions.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              @PathVariable Long id,
                                              @AuthenticationPrincipal UserDetails userDetails) {

        if (isOwnerOrAdmin(userDetails, id)) {
            HttpStatus status = userService.updateUser(id, userDto);

            //TODO handle when user updating username or password

            userDto = userService.findById(id);
            return ResponseEntity.status(status).body(userDto);
        }
        throw new AccessDeniedException("You do not have the right permissions");
    }

    /**
     * Delete a user by ID.
     *
     * @param id          The ID of the user to delete.
     * @param userDetails The UserDetails of the authenticated user.
     * @return ResponseEntity with a message indicating the result of the deletion.
     * @throws AccessDeniedException if the requester does not have the right permissions.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        if (isOwnerOrAdmin(userDetails, id)) {
            HttpStatus responseStatus = userService.deleteUser(id);
            HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate();
            }
            SecurityContextHolder.clearContext();
            return ResponseEntity.status(responseStatus).body("User successfully deleted, you have been logged out");
        }
        throw new AccessDeniedException("You do not have the right permissions");
    }

    /**
     * Check if the user is the owner of the resource or has admin privileges.
     *
     * @param userDetails The UserDetails of the authenticated user.
     * @param id          The ID of the user to check ownership.
     * @return true if the user is the owner or has admin privileges, false otherwise.
     */
    private boolean isOwnerOrAdmin(UserDetails userDetails, Long id) {
        UserDto userDto = userService.findById(id);
        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()))
                || userDetails.getUsername().equals(userDto.getUsername());
    }

}
