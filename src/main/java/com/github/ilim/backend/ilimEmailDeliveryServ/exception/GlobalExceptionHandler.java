package com.github.ilim.backend.ilimEmailDeliveryServ.exception;

import com.github.ilim.backend.ilimEmailDeliveryServ.model.EmailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 *
 * Author: praveendran
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors.
     *
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MailSendException.
     *
     * @param ex MailSendException
     * @return ResponseEntity with error message
     */
    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<EmailResponse> handleMailSendException(MailSendException ex) {
        EmailResponse response = new EmailResponse("Failed to send email: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles RateLimitExceededException.
     *
     * @param ex RateLimitExceededException
     * @return ResponseEntity with error message
     */
    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<EmailResponse> handleRateLimitExceededException(RateLimitExceededException ex) {
        EmailResponse response = new EmailResponse(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS.value());
        return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
    }

    /**
     * Handles all other exceptions.
     *
     * @param ex Exception
     * @return ResponseEntity with error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<EmailResponse> handleAllExceptions(Exception ex) {
        // Log the exception as needed
        EmailResponse response = new EmailResponse("An unexpected error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}