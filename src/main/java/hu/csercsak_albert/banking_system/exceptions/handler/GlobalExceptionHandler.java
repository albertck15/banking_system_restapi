package hu.csercsak_albert.banking_system.exceptions.handler;

import hu.csercsak_albert.banking_system.dto.CustomErrorResponse;
import hu.csercsak_albert.banking_system.exceptions.*;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

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

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidAmountException(InvalidAmountException e) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BalanceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleBalanceNotFoundException(BalanceNotFoundException e) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce("", (accumulator, item) -> accumulator + "\n" + item);
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
}
