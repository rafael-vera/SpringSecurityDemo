package org.rafaelvera.springsecuritydemo.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl extends EmailService {
    public EmailServiceImpl(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Override
    public void sendVerificationMessage(String username, String token) {
        try {
            MimeMessage message = super.createMessage(
                    new String[]{username},
                    "Verify your account",
                    "You can verify your account by clicking this link:\n"+VERIFICATION_LINK+token,
                    false
            );

            super.sendEmail(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
