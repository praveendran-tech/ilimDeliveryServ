package com.github.ilim.backend.ilimEmailDeliveryServ.config;

import com.github.ilim.backend.ilimEmailDeliveryServ.filter.RateLimitingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for registering servlet filters.
 *
 * Author: praveendran
 */
@Configuration
public class FilterConfig {

    private final RateLimitingFilter rateLimitingFilter;

    @Autowired
    public FilterConfig(RateLimitingFilter rateLimitingFilter) {
        this.rateLimitingFilter = rateLimitingFilter;
    }

    /**
     * Registers the RateLimitingFilter to apply to specific URL patterns.
     *
     * @return FilterRegistrationBean for RateLimitingFilter
     */
    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilterRegistration() {
        FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(rateLimitingFilter);
        registrationBean.addUrlPatterns("/api/email/*"); // Apply to specific URL patterns
        registrationBean.setOrder(1); // Set precedence if multiple filters
        return registrationBean;
    }
}