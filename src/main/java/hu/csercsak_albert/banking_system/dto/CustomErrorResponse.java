package hu.csercsak_albert.banking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CustomErrorResponse {

    private final String message;

    private final LocalDateTime timestamp;
}
