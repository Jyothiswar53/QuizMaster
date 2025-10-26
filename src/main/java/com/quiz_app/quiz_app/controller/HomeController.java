package com.quiz_app.quiz_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/select-level")
    public String selectDifficulty() {
        return "select-level"; 
    }
}