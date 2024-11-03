package com.github.ilim.backend.ilimEmailDeliveryServ.controller;



import com.github.ilim.backend.ilimEmailDeliveryServ.model.EmailRequest;
import com.github.ilim.backend.ilimEmailDeliveryServ.model.EmailResponse;
import com.github.ilim.backend.ilimEmailDeliveryServ.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling email-related operations.
 *
 * Author: praveendran
 */
@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@Valid @RequestBody EmailRequest request) {
        emailService.sendEmail(request);
        EmailResponse response = new EmailResponse("Email sent successfully!", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
