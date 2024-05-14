package com.foody.authservice.persistence;

import com.foody.authservice.persistence.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredential,Long> {
    Optional<UserCredential> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndId(String email, Long id);
    boolean existsByUsername(String username);

    boolean existsByUsernameAndId(String username, Long id);
    @Query("SELECT COUNT(u) > 0 FROM UserCredential u WHERE u.email = :email AND u.id != :userId")
    boolean existsByEmailExcludingUserId(String email, Long userId);

    @Query("SELECT COUNT(u) > 0 FROM UserCredential u WHERE u.username = :username AND u.id != :userId")
    boolean existsByUsernameExcludingUserId(String username, Long userId);
}
