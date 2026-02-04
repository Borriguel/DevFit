package dev.borriguel.devfit.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleResourceNotFound(ResourceNotFound ex, HttpServletRequest request) {
        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ValidationException ex, HttpServletRequest request) {
        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(BusinessRuleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleBusinessRuleException(BusinessRuleException ex, HttpServletRequest request) {
        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ApiError.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ApiError.ValidationError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()))
                .toList();
        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation error",
                request.getRequestURI(),
                validationErrors
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleAuthorizationDeniedException(AuthorizationDeniedException ex, HttpServletRequest request) {
        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGenericException(HttpServletRequest request) {
        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error occurred",
                request.getRequestURI(),
                null
        );
    }

}
