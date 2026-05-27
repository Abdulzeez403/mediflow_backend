package com.mediflow.mediflow.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@mediflow.com}")
    private String fromEmail;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Welcome to MediFlow!");
        message.setText("Hello " + name + ",\n\n" +
                "Welcome to MediFlow! Your account has been successfully created.\n\n" +
                "Best regards,\n" +
                "The MediFlow Team");
        
        mailSender.send(message);
    }

    public void sendEmailVerification(String to, String name, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Verify Your Email - MediFlow");
        String verificationLink = frontendUrl + "/verify-email?token=" + token;
        message.setText("Hello " + name + ",\n\n" +
                "Please verify your email address by clicking the link below:\n\n" +
                verificationLink + "\n\n" +
                "This link will expire in 24 hours.\n\n" +
                "If you did not create this account, please ignore this email.\n\n" +
                "Best regards,\n" +
                "The MediFlow Team");
        
        mailSender.send(message);
    }

    public void sendResetToken(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Password Reset Request - MediFlow");
        String resetLink = frontendUrl + "/reset-password?token=" + token;
        message.setText("Hello,\n\n" +
                "You have requested to reset your password. Please click the link below:\n\n" +
                resetLink + "\n\n" +
                "This link will expire in 30 minutes.\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "The MediFlow Team");
        
        mailSender.send(message);
    }

    public void sendPasswordChangeConfirmation(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Password Changed Successfully - MediFlow");
        message.setText("Hello " + name + ",\n\n" +
                "Your password has been successfully changed.\n\n" +
                "If you did not make this change, please contact support immediately.\n\n" +
                "Best regards,\n" +
                "The MediFlow Team");
        
        mailSender.send(message);
    }

    public void sendAccountLockedNotification(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Account Locked - MediFlow");
        message.setText("Hello " + name + ",\n\n" +
                "Your account has been locked due to multiple failed login attempts.\n\n" +
                "Please contact support to unlock your account.\n\n" +
                "Best regards,\n" +
                "The MediFlow Team");
        
        mailSender.send(message);
    }
}
