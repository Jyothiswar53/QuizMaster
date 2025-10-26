package com.quiz_app.quiz_app.controller;

import com.quiz_app.quiz_app.model.QuizAttempt;
import com.quiz_app.quiz_app.model.User;
import com.quiz_app.quiz_app.service.QuizService;
import com.quiz_app.quiz_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuizService quizService;

    @GetMapping("/result")
    public String showResult(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        if (user == null) {
            return "redirect:/login";
        }

        QuizAttempt latestAttempt = quizService.getLatestAttempt(user);

        model.addAttribute("attempt", latestAttempt);
        return "result";
    }
}