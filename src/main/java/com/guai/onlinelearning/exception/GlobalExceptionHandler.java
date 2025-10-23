package com.guai.onlinelearning.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleException(JwtException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorResponse(
                                StatusCode.UNAUTHORIZED,
                                "Login Failed",
                                ex.getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorResponse(
                                StatusCode.UNAUTHORIZED,
                                "Login Failed",
                                ex.getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorResponse(
                                StatusCode.UNAUTHORIZED,
                                "Login Failed",
                                ex.getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
        Set<ValidationErrorResponse> violationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(errors ->
                        ValidationErrorResponse.builder()
                                .field(errors.getField())
                                .message(errors.getDefaultMessage())
                                .build()
                )
                .collect(Collectors.toSet());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(
                                StatusCode.BAD_REQUEST,
                                "Validation Failed",
                                null,
                                violationErrors
                        )
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof ConstraintViolationException) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(buildErrorResponse(
                                    StatusCode.CONFLICT,
                                    "A record with this unique value already exists.",
                                    ex.getMessage(),
                                    null
                            )
                    );
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse(
                                StatusCode.INTERNAL_SERVER_ERROR,
                                null,
                                ex.getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(
                                StatusCode.BAD_REQUEST,
                                null,
                                ex.getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse(
                                StatusCode.INTERNAL_SERVER_ERROR,
                                null,
                                ex.getMessage(),
                                null
                        )
                );
    }

    private ErrorResponse buildErrorResponse(StatusCode statusCode, String message, String error, Set<ValidationErrorResponse> errors) {
        return ErrorResponse.builder()
                .statusCode(statusCode.getCode())
                .status(statusCode.getMessage())
                .timestamp(LocalDateTime.now())
                .message(message)
                .error(error)
                .errors(errors)
                .build();
    }

}
