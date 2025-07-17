package com.tech.challenge.TechChallenge.utils;

import com.tech.challenge.TechChallenge.utils.exceptions.InvalidCredentialsException;
import com.tech.challenge.TechChallenge.utils.exceptions.InvalidPasswordException;
import com.tech.challenge.TechChallenge.utils.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentials(final InvalidCredentialsException ex,
                                                                     final HttpServletRequest request) {
        final ErrorResponseDTO response = new ErrorResponseDTO(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentials(final InvalidPasswordException ex,
                                                                     final HttpServletRequest request) {
        final ErrorResponseDTO response = new ErrorResponseDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentials(final UserNotFoundException ex, final HttpServletRequest request) {
        final ErrorResponseDTO response = new ErrorResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
