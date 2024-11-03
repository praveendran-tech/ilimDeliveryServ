package com.github.ilim.backend.ilimEmailDeliveryServ.filter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.ilim.backend.ilimEmailDeliveryServ.exception.RateLimitExceededException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Filter to apply rate limiting on incoming API requests based on client IP.
 *
 * Author: praveendran
 */
@Component
public class RateLimitingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingFilter.class);

    private final Cache<String, Bucket> cache;
    private final Bandwidth bandwidthLimit;

    @Autowired
    public RateLimitingFilter(Cache<String, Bucket> cache, Bandwidth bandwidthLimit) {
        this.cache = cache;
        this.bandwidthLimit = bandwidthLimit;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    /**
     * Applies rate limiting logic before processing the request.
     *
     * @param request  the incoming HttpServletRequest
     * @param response the outgoing ServletResponse
     * @param chain    the FilterChain
     * @throws IOException      in case of I/O errors
     * @throws ServletException in case of general servlet errors
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String ip = getClientIP(httpRequest);

            // Retrieve or create a Bucket for the IP
            Bucket bucket = cache.get(ip, k -> Bucket.builder()
                    .addLimit(bandwidthLimit)
                    .build());

            // Attempt to consume a token
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
            if (probe.isConsumed()) {
                // Set rate limit headers
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.setContentType("application/json");
                httpResponse.setHeader("X-Rate-Limit-Limit", String.valueOf(bandwidthLimit.getCapacity()));
                httpResponse.setHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));

                logger.info("Request allowed for IP: {}. Remaining tokens: {}", ip, probe.getRemainingTokens());

                // Proceed with the request
                chain.doFilter(request, response);
            } else {
                // Rate limit exceeded; throw custom exception
                logger.warn("Rate limit exceeded for IP: {}", ip);
                throw new RateLimitExceededException("Too many requests. Please try again later.");
            }
        } else {
            // Non-HTTP request; proceed without rate limiting
            chain.doFilter(request, response);
        }
    }

    /**
     * Extracts the client's IP address from the HttpServletRequest.
     *
     * @param request the HttpServletRequest
     * @return the client's IP address as a String
     */
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }

}