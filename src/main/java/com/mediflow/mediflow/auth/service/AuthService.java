package com.mediflow.mediflow.auth.service;

import com.mediflow.mediflow.auth.dto.*;
import com.mediflow.mediflow.user.entity.User;
import com.mediflow.mediflow.user.repository.UserRepository;
import com.mediflow.mediflow.user.entity.UserRole;
import com.mediflow.mediflow.user.entity.UserStatus;
import com.mediflow.mediflow.auth.security.JwtService;
import com.mediflow.mediflow.common.service.EmailService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {

    private final UserRepository repo;

    private final JwtService jwtService;

    private final BCryptPasswordEncoder encoder;

    private final EmailService emailService;
    
    private final TokenBlacklistService tokenBlacklistService;

    public AuthService(UserRepository repo, JwtService jwtService, BCryptPasswordEncoder encoder, EmailService emailService, TokenBlacklistService tokenBlacklistService) {
        this.repo = repo;
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.emailService = emailService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public String register(User user) {

        // remove spaces
        user.setEmail(user.getEmail().trim().toLowerCase());

        // check if email already exists
        if (repo.findByEmail(user.getEmail()).isPresent()) {

            throw new RuntimeException("Email already exists");
        }

        // validate password length
        if (user.getPassword().length() < 6) {

            throw new RuntimeException(
                    "Password must be at least 6 characters"
            );
        }

        // validate name
        if (
                user.getName() == null ||
                user.getName().trim().isEmpty()
        ) {

            throw new RuntimeException("Name is required");
        }

        // hash password
        user.setPassword(
                encoder.encode(user.getPassword())
        );

        // default role
        user.setRole(UserRole.CUSTOMER);

        // default status
        user.setStatus(UserStatus.ACTIVE);

        // generate email verification token
        String verificationToken = UUID.randomUUID().toString();
        user.setEmailVerificationToken(verificationToken);
        user.setEmailVerificationExpiresAt(LocalDateTime.now().plusHours(24));

        // save user
        repo.save(user);

        // send verification email
        // emailService.sendEmailVerification(user.getEmail(), user.getName(), verificationToken);

        // generate token
        return jwtService.generateToken(user);
    }

    public String login(String email, String password) {

        email = email.trim().toLowerCase();

        User user = repo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid email or password"
                        )
                );

        // check account status
        if (user.getStatus() == UserStatus.SUSPENDED) {

            throw new RuntimeException("Account suspended");
        }

        // verify password
        if (
                !encoder.matches(
                        password,
                        user.getPassword()
                )
        ) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        // update last login
        user.setLastLoginAt(LocalDateTime.now());

        repo.save(user);

        // generate token
        return jwtService.generateToken(user);
    }
     


    
    public String forgotPassword(String email){

        User user = repo.findByEmail(email)
        .orElseThrow(()->
            new RuntimeException(
                "User not found"
            )
        );

        String token = UUID.randomUUID().toString();
        user.setRestPasswordToken(token);
        user.setRestPasswordExpiresAt(LocalDateTime.now().plusMinutes(30));
        repo.save(user);
        
        // send email
        emailService.sendResetToken(user.getEmail(), token);
        
        return "Reset token sent to email";
    }


    public String changePassword(String email, ChangePasswordRequest changePasswordRequest){
        User user = repo.findByEmail(email)
        .orElseThrow(()->
            new RuntimeException(
                "User not found"
            )
        );

        // verify current password
        if (!encoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // validate new password length
        if (changePasswordRequest.getNewPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        // hash new password
        user.setPassword(
                encoder.encode(changePasswordRequest.getNewPassword())
        );

        repo.save(user);

        // send confirmation email
        emailService.sendPasswordChangeConfirmation(user.getEmail(), user.getName());

        return "Password changed successfully";
    }

    public ResetPasswordResponse getResetPasswordInfo(String token){
        User user = repo.findByRestPasswordToken(token)
        .orElseThrow(()->
            new RuntimeException(
                "Invalid token"
            )
        );

        // check if token is expired
        if (user.getRestPasswordExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        return new ResetPasswordResponse(
            user.getEmail(),
            user.getName(),
            user.getPhone(),
            user.getDateOfBirth(),
            user.getGender()
        );
    }

    public String resetPassword(ResetPasswordRequest request){
        User user = repo.findByRestPasswordToken(request.getToken())
        .orElseThrow(()->
            new RuntimeException(
                "Invalid token"
            )
        );

        // check if token is expired
        if (user.getRestPasswordExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        // validate password length
        if (request.getNewPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        // hash new password
        user.setPassword(
                encoder.encode(request.getNewPassword())
        );

        // clear token fields
        user.setRestPasswordToken(null);
        user.setRestPasswordExpiresAt(null);

        repo.save(user);

        // send confirmation email
        emailService.sendPasswordChangeConfirmation(user.getEmail(), user.getName());

        return "Password reset successfully";
    }

    public String verifyEmail(String token){
        User user = repo.findByEmailVerificationToken(token)
        .orElseThrow(()->
            new RuntimeException(
                "Invalid verification token"
            )
        );

        // check if token is expired
        if (user.getEmailVerificationExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification token expired");
        }

        // mark email as verified
        user.setEmailVerifiedAt(LocalDateTime.now());
        user.setEmailVerificationToken(null);
        user.setEmailVerificationExpiresAt(null);

        repo.save(user);

        // send welcome email after verification
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());

        return "Email verified successfully";
    }

    public String logout(String token, String email) {
        // Extract expiration time from token
        LocalDateTime expiresAt = jwtService.extractExpiration(token);
        
        // Add token to blacklist
        tokenBlacklistService.blacklistToken(token, email, expiresAt);
        
        return "Logout successful";
    }
}
