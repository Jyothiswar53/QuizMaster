package com.quiz_app.quiz_app.controller;

import com.quiz_app.quiz_app.model.Question;
import com.quiz_app.quiz_app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String showAdminPanel(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("questions", adminService.getAllQuestions());
        return "admin";
    }

    @PostMapping("/question")
    public String saveQuestion(@ModelAttribute Question question) {
        adminService.saveQuestion(question);
        return "redirect:/admin";
    }

    @GetMapping("/question/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        adminService.deleteQuestion(id);
        return "redirect:/admin";
    }

    @GetMapping("/analytics")
    public String showAnalytics(Model model) {
        model.addAttribute("analytics", adminService.getAnalytics());
        return "analytics";
    }
}