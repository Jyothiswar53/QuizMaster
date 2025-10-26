package com.quiz_app.quiz_app.controller;

import com.quiz_app.quiz_app.dto.LeaderboardEntry;
import com.quiz_app.quiz_app.repository.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LeaderboardController {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @GetMapping("/leaderboard")
    public String showLeaderboard(@RequestParam(defaultValue = "EASY") String difficulty, Model model) {

        List<Object[]> rawData = quizAttemptRepository.findBestScoresByDifficulty(difficulty);

        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        int rank = 1;

        for (Object[] row : rawData) {
            String username = (String) row[0];

            Integer scoreObject = (Integer) row[1];
            int score = scoreObject.intValue();

            LeaderboardEntry entry = new LeaderboardEntry(username, score);
            entry.setRank(rank++);
            leaderboard.add(entry);
        }

        model.addAttribute("leaderboard", leaderboard);
        model.addAttribute("selectedDifficulty", difficulty.toUpperCase());

        return "leaderboard";
    }
}