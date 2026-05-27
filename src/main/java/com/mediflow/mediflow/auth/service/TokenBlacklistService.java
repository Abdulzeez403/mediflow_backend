package com.mediflow.mediflow.auth.service;

import com.mediflow.mediflow.auth.entity.TokenBlacklist;
import com.mediflow.mediflow.auth.repository.TokenBlacklistRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TokenBlacklistService {
    
    private final TokenBlacklistRepository repository;
    
    public TokenBlacklistService(TokenBlacklistRepository repository) {
        this.repository = repository;
    }
    
    public void blacklistToken(String token, String userEmail, LocalDateTime expiresAt) {
        TokenBlacklist blacklistedToken = new TokenBlacklist(token, userEmail, expiresAt);
        repository.save(blacklistedToken);
    }
    
    public boolean isTokenBlacklisted(String token) {
        return repository.findByToken(token).isPresent();
    }
    
    public void cleanupExpiredTokens() {
        repository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
