package com.mediflow.mediflow.user.repository;
import com.mediflow.mediflow.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
   Optional<User> findByEmail(String email);
   Optional<User> findById(String id);
   Optional<User> findByRestPasswordToken(String token);
   Optional<User> findByEmailVerificationToken(String token);

}