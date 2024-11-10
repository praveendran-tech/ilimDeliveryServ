package com.github.ilim.backend.ilimEmailDeliveryServ.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing an email request.
 * It encapsulates the necessary details required to send an email.
 *
 * <p>This class includes validation annotations to ensure that all
 * required fields are provided and adhere to the expected formats.</p>
 *
 * <p>Author: praveendran</p>
 */
public class EmailRequest {

    @NotBlank(message = "To address is mandatory")
    @Email(message = "To address should be a valid email")
    private String toAddress;

    private List<@Email(message = "Each CC address should be a valid email") String> ccAddresses; // List of CC addresses (optional)

    @NotBlank(message = "Subject is mandatory")
    private String subject;

    @NotBlank(message = "Content is mandatory")
    private String content;

    // Constructors
    public EmailRequest() {
    }

    public EmailRequest(String toAddress, List<String> ccAddresses, String subject, String content) {
        this.toAddress = toAddress;
        this.ccAddresses = ccAddresses;
        this.subject = subject;
        this.content = content;
    }

    // Getters and Setters
    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public List<String> getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(List<String> ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
