package com.smartshop.presontation.config;

import com.smartshop.domain.Excption.InvalidCredentialsException;
import com.smartshop.domain.Excption.InvalidOrderStateException;
import com.smartshop.domain.Excption.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentialsErrors(InvalidCredentialsException ex, HttpServletRequest request) {
        Map<String, String> errorBody = new LinkedHashMap<>();
        errorBody.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        errorBody.put("error", "UNAUTHORIZED");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorBody,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
                errors.put("status", String.valueOf( HttpStatus.BAD_REQUEST.value()));
                errors.put("error", errors.toString());
                errors.put("message", ex.getMessage());
                errors.put("path", request.getRequestURI());

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", String.valueOf( HttpStatus.NOT_FOUND.value()));
        errors.put("error", "NOT_FOUND");
        errors.put("message", ex.getMessage());
        errors.put("path", request.getRequestURI());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOrderStateException.class)
    public ResponseEntity<Map<String, String>> handleInvalidState(InvalidOrderStateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "CONFLICT");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
