package com.foody.authservice.persistence;

import com.foody.authservice.persistence.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredential,Long> {
    Optional<UserCredential> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndId(String email, Long id);
    boolean existsByUsername(String username);

    boolean existsByUsernameAndId(String username, Long id);
}
