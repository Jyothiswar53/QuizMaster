package com.quiz_app.quiz_app.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
}