package com.mediflow.mediflow.user.dto;

import com.mediflow.mediflow.user.entity.UserRole;
import com.mediflow.mediflow.user.entity.UserStatus;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime lastLoginAt;
    private LocalDateTime emailVerifiedAt;
    private LocalDateTime phoneVerifiedAt;
    
    
    public UserDTO(UUID id, String name, String email, String phone, LocalDate dateOfBirth, 
                   String gender, UserRole role, UserStatus status, LocalDateTime lastLoginAt, 
                   LocalDateTime emailVerifiedAt, LocalDateTime phoneVerifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.lastLoginAt = lastLoginAt;
        this.emailVerifiedAt = emailVerifiedAt;
        this.phoneVerifiedAt = phoneVerifiedAt;
    }

      

}
