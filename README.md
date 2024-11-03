# Ilim Email Delivery Service


## Overview
Ilim Email Delivery Service is a robust Spring Boot application designed to handle email sending operations efficiently. It incorporates essential features like rate limiting to prevent abuse and integrates Swagger UI for comprehensive API documentation and testing. The application ensures secure and scalable email delivery with user-friendly interfaces for developers and administrators.

## Features
- **Send Emails with CC:** Facilitates sending emails with the ability to add carbon copy (CC) recipients.
- **Rate Limiting:** Restricts clients to a maximum of 100 requests per minute per IP address to ensure fair usage and prevent abuse.
- **API Documentation:** Provides interactive API documentation and testing capabilities via Swagger UI.
- **Global Exception Handling:** Centralizes error handling to deliver consistent and informative responses.
- **Security Configurations:** Protects API endpoints using Spring Security, ensuring that only authorized users can access sensitive operations.
- **Monitoring and Diagnostics:** Utilizes Spring Boot Actuator for comprehensive monitoring and health checks.
