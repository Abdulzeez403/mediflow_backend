package com.mediflow.mediflow.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "token_blacklist")
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "TEXT")
    private String token;
    
    private String userEmail;
    
    private LocalDateTime blacklistedAt;
    
    private LocalDateTime expiresAt;
    
    public TokenBlacklist() {}
    
    public TokenBlacklist(String token, String userEmail, LocalDateTime expiresAt) {
        this.token = token;
        this.userEmail = userEmail;
        this.blacklistedAt = LocalDateTime.now();
        this.expiresAt = expiresAt;
    }
}
