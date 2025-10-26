package com.quiz_app.quiz_app.service;

import com.quiz_app.quiz_app.dto.SignupRequest;
import com.quiz_app.quiz_app.model.User;
import com.quiz_app.quiz_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(SignupRequest request) throws Exception {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new Exception("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}