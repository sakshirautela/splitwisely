package com.splitwisely.backend.repository;

import com.splitwisely.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    Optional<org.springframework.security.core.userdetails.User> findByUsername(String username);

}
