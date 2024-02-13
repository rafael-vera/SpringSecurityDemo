package org.rafaelvera.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
public class Config {
    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.protocol}")
    private String mailProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean mailSmtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean mailSmtpStarttlsEnable;

    @Value("${spring.mail.properties.mail.debug}")
    private boolean mailSmtpDebug;

    @Bean
    JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.mailHost);
        mailSender.setPort(this.mailPort);

        mailSender.setUsername(this.mailUsername);
        mailSender.setPassword(this.mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", this.mailProtocol);
        props.put("mail.smtp.auth", this.mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", this.mailSmtpStarttlsEnable);
        props.put("mail.debug", this.mailSmtpDebug);

        return mailSender;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
