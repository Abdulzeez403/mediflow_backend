package com.mediflow.mediflow.auth.dto;

import jakarta.validation.constraints.*;
public class ForgotPasswordRequest{

    @Email
    @NotBlank
    private String email;

    public String getEmail(){
        return email;
    }

}
