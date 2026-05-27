package com.mediflow.mediflow.auth.repository;

import com.mediflow.mediflow.auth.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    Optional<TokenBlacklist> findByToken(String token);
    void deleteByExpiresAtBefore(java.time.LocalDateTime dateTime);
}
