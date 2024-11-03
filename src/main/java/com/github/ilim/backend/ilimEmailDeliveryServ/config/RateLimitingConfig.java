package com.github.ilim.backend.ilimEmailDeliveryServ.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration class for setting up rate limiting using Bucket4j and Caffeine.
 *
 * Author: praveendran
 */
@Configuration
@ConfigurationProperties(prefix = "rate-limiting")
public class RateLimitingConfig {

    private int capacity;
    private Duration refillDuration;

    // Getters and Setters

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Duration getRefillDuration() {
        return refillDuration;
    }

    public void setRefillDuration(Duration refillDuration) {
        this.refillDuration = refillDuration;
    }

    /**
     * Creates a Caffeine cache to store buckets for each IP address.
     *
     * @return Cache mapping IP addresses to their corresponding buckets
     */
    @Bean
    public Cache<String, io.github.bucket4j.Bucket> cache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(1)) // Expire entries after 1 hour of inactivity
                .maximumSize(10_000) // Maximum number of entries in the cache
                .build();
    }

    /**
     * Defines the bandwidth limit for rate limiting based on configuration properties.
     *
     * @return Bandwidth limit configuration
     */
    @Bean
    public Bandwidth bandwidthLimit() {
        // Allow 'capacity' requests per 'refillDuration'
        return Bandwidth.classic(capacity, Refill.greedy(capacity, refillDuration));
    }
}