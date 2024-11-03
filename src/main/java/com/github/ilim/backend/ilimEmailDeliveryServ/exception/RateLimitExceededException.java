package com.github.ilim.backend.ilimEmailDeliveryServ.exception;

/**
 * Exception thrown when a client exceeds the rate limit.
 *
 * Author: praveendran
 */
public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message) {
        super(message);
    }
}