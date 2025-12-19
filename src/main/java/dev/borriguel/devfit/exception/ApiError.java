package dev.borriguel.devfit.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
    LocalDateTime timestamp,
    Integer status,
    String error,
    String message,
    String path,
    List<ValidationError> errors
) {

    public record ValidationError(
        String field,
        String message
    ) {
    }
}
