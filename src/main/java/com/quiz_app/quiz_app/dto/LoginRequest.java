package com.quiz_app.quiz_app.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}