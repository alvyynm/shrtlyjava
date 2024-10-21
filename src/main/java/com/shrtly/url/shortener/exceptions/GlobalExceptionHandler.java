package com.shrtly.url.shortener.exceptions;

import com.shrtly.url.shortener.utils.StandardApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<StandardApiResponse> handleJwtException(JwtException ex) {
        StandardApiResponse response = new StandardApiResponse(false, "Invalid or expired token", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Handle generic authentication exceptions
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        StandardApiResponse response = new StandardApiResponse(false, "Unauthorized access", null);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<StandardApiResponse> handleUrlNotFoundException(UrlNotFoundException ex) {
        StandardApiResponse response = new StandardApiResponse(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardApiResponse> handleGenericException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
//                body(Map.of("error", "An error occurred: " + ex.getMessage()));
        StandardApiResponse response = new StandardApiResponse(false, "An error occurred", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
