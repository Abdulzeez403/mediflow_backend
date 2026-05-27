package com.mediflow.mediflow.auth.controller;

import com.mediflow.mediflow.auth.dto.*;
import com.mediflow.mediflow.user.entity.User;
import com.mediflow.mediflow.auth.service.AuthService;


import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody User user
    ) {

        return new AuthResponse(service.register(user));
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request
    ) {

        return new AuthResponse(service.login(
                request.getEmail(),
                request.getPassword()
        ));
    }


    @PostMapping("/forgot-password")
    public String forgotPassword(
        @Valid @RequestBody ForgotPasswordRequest request
    ){
        return service.forgotPassword(request.getEmail());
    }

    @GetMapping("/reset-password")
    public ResetPasswordResponse resetPassword(
        @RequestParam String token
    ){
        return service.getResetPasswordInfo(token);
    }

    @PostMapping("/reset-password")
    public String submitResetPassword(
        @Valid @RequestBody ResetPasswordRequest request
    ){
        return service.resetPassword(request);
    }
    
    @PostMapping("/change-password")
    public String changePassword(
        @Valid @RequestBody ChangePasswordRequest request,
        Authentication authentication
    ){
        String email = authentication.getName();
        return service.changePassword(email, request);
    }

    @GetMapping("/verify-email")
    public String verifyEmail(
        @RequestParam String token
    ){
        return service.verifyEmail(token);
    }

    @PostMapping("/logout")
    public String logout(
        @RequestHeader("Authorization") String authHeader,
        Authentication authentication
    ){
        String token = authHeader.substring(7);
        String email = authentication.getName();
        return service.logout(token, email);
    }
}