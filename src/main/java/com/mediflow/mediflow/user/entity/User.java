package com.mediflow.mediflow.user.entity;



import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;


import java.time.LocalDate;
import java.util.UUID;

import com.mediflow.mediflow.user.dto.UserDTO;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotBlank
    private String name;
    
    @Email
    @NotBlank
    @Column(unique =true)
    private String email;

   @NotBlank
   @Size(min = 6)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime emailVerifiedAt;
    private LocalDateTime phoneVerifiedAt;
    private LocalDateTime lastLoginAt;
    private LocalDate dateOfBirth;
    private String gender;
    

    @NotBlank
    @Size(min = 11)
    private String phone;

    private String restPasswordToken;
    private LocalDateTime restPasswordExpiresAt;

    private String emailVerificationToken;
    private LocalDateTime emailVerificationExpiresAt;
   

      public UserDTO toDTO() {
        return new UserDTO(
            this.id,
            this.name,
            this.email,
            this.phone,
            this.dateOfBirth,
            this.gender,
            this.role,
            this.status,
            this.lastLoginAt,
            this.emailVerifiedAt,
            this.phoneVerifiedAt
        );
    }


    
}
