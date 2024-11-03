package com.github.ilim.backend.ilimEmailDeliveryServ.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Swagger/OpenAPI documentation.
 *
 * Author: praveendran
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ilimDeliveryServOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Ilim Email Delivery Service API")
                        .description("API documentation for Ilim Email Delivery Service.\n\n" +
                                "**Rate Limiting:** 100 requests per minute per IP address.")
                        .version("v1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/praveendran-tech/ilimDeliveryServ"));
    }
}