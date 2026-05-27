package com.mediflow.mediflow.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    public String name;
    public String email;
    public String password;
 }
