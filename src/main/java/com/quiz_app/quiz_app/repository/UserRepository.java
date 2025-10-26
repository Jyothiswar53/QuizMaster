package com.quiz_app.quiz_app.repository;

import com.quiz_app.quiz_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}