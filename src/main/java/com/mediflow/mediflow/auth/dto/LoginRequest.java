package com.mediflow.mediflow.auth.dto;

import jakarta.validation.constraints.*;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    @Email
    private String email;

    @Size(min =6)
    private String  password;
}
