package com.mediflow.mediflow.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordResponse {
    private String email;
    private String name;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
}
