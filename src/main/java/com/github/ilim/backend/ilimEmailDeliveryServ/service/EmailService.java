package com.github.ilim.backend.ilimEmailDeliveryServ.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.ilim.backend.ilimEmailDeliveryServ.model.EmailRequest;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;

/**
 * Service for sending emails.
 *
 * Handles the logic of composing and sending emails using SMTP server.
 *
 * Author: praveendran
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email based on the provided EmailRequest.
     *
     * @param emailRequest the email request payload
     * @throws MailSendException if sending the email fails
     */
    public void sendEmail(EmailRequest emailRequest) throws MailSendException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(emailRequest.getToAddress());

            // Handle optional list of CC addresses
            List<String> ccAddresses = emailRequest.getCcAddresses();
            if (ccAddresses != null && !ccAddresses.isEmpty()) {
                helper.setCc(ccAddresses.toArray(new String[0]));
            }

            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getContent(), true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Wrap and rethrow the exception for the controller to handle
            throw new MailSendException("Error while sending email", e);
        } catch (MailException e) {
            throw new MailSendException("Mail server error", e);
        }
    }
}
