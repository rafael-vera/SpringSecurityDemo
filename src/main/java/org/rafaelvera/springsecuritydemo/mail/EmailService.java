package org.rafaelvera.springsecuritydemo.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class EmailService {
    private static final String NO_REPLY_ADDRESS = "noreply@example.com";

    private final JavaMailSender mailSender;

    protected static final String VERIFICATION_LINK = "http://localhost:8080/api/v1/public/verify?token=";

    protected void sendEmail(MimeMessage message) {
        this.mailSender.send(message);
    }

    protected MimeMessage createMessage(
            String[] to,
            String subject,
            String text,
            boolean isHtmlText
    ) throws MessagingException {
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(NO_REPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, isHtmlText);

        return message;
    }

    public abstract void sendVerificationMessage(String username, String token);
}
