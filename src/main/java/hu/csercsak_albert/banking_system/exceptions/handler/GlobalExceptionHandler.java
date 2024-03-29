package hu.csercsak_albert.banking_system.exceptions.handler;

import hu.csercsak_albert.banking_system.dto.CustomErrorResponse;
import hu.csercsak_albert.banking_system.exceptions.AccountNotFoundException;
import hu.csercsak_albert.banking_system.exceptions.InvalidAmountException;
import hu.csercsak_albert.banking_system.exceptions.TransactionNotFoundException;
import hu.csercsak_albert.banking_system.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleAccountNotFoundException(AccountNotFoundException e) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidAmountException(InvalidAmountException e) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("\n"));
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message("Email or username already taken")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleTransactionNotFoundException(TransactionNotFoundException e) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
