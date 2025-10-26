package com.quiz_app.quiz_app.controller;

import com.quiz_app.quiz_app.dto.SignupRequest;
import com.quiz_app.quiz_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute SignupRequest signupRequest, Model model) {
        try {
            userService.registerUser(signupRequest);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("signupError", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
}