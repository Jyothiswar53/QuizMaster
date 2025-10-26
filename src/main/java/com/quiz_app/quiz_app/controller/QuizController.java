package com.quiz_app.quiz_app.controller;

import com.quiz_app.quiz_app.dto.QuizSubmissionDto;
import com.quiz_app.quiz_app.model.Question;
import com.quiz_app.quiz_app.model.QuizAttempt;
import com.quiz_app.quiz_app.model.User;
import com.quiz_app.quiz_app.service.QuizService;
import com.quiz_app.quiz_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    @GetMapping("/quiz")
    public String showQuiz(@RequestParam(required = false) String difficulty, Model model) {
        if (difficulty == null || difficulty.isBlank()) {
            return "redirect:/select-level";
        }

        List<Question> questions = quizService.getQuestions(difficulty.toLowerCase(), 10);

        QuizSubmissionDto submission = new QuizSubmissionDto();
        submission.setDifficulty(difficulty.toUpperCase());

        List<Long> questionIds = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        for (Question q : questions) {
            questionIds.add(q.getId());
            answers.add("");
        }
        submission.setQuestionIds(questionIds);
        submission.setAnswers(answers);

        model.addAttribute("questions", questions);
        model.addAttribute("submission", submission);
        model.addAttribute("difficulty", difficulty.toUpperCase());

        return "quiz";
    }

    @PostMapping("/quiz/submit")
    public String submitQuiz(@ModelAttribute QuizSubmissionDto submission, RedirectAttributes redirectAttributes) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        QuizAttempt attempt = quizService.submitQuiz(user, submission);
        attempt.setTotalQuestions(submission.getQuestionIds().size());

        redirectAttributes.addAttribute("id", attempt.getId());
        return "redirect:/result/{id}";
    }

    @GetMapping("/result/{id}")
    public String showResult(@PathVariable Long id, Model model) {
        QuizAttempt attempt = quizService.getAttemptById(id);
        model.addAttribute("attempt", attempt);
        return "result";
    }
}