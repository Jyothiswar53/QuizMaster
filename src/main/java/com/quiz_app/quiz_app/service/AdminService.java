package com.quiz_app.quiz_app.service;

import com.quiz_app.quiz_app.model.Question;
import com.quiz_app.quiz_app.model.QuizAttempt;
import com.quiz_app.quiz_app.repository.QuestionRepository;
import com.quiz_app.quiz_app.repository.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

        @Autowired
        private QuestionRepository questionRepository;

        @Autowired
        private QuizAttemptRepository quizAttemptRepository;

        public Question saveQuestion(Question question) {
                return questionRepository.save(question);
        }

        public void deleteQuestion(Long id) {
                questionRepository.deleteById(id);
        }

        public List<Question> getAllQuestions() {
                return questionRepository.findAll();
        }

        public Map<String, Number> getAnalytics() {
                List<QuizAttempt> attempts = quizAttemptRepository.findAll();

                long totalUsers = attempts.stream()
                                .map(a -> a.getUser().getId())
                                .distinct()
                                .count();

                double avgScore = attempts.stream()
                                .mapToInt(QuizAttempt::getScore)
                                .average()
                                .orElse(0);

                long easyCount = attempts.stream()
                                .filter(a -> "EASY".equalsIgnoreCase(a.getDifficulty()))
                                .count();

                long mediumCount = attempts.stream()
                                .filter(a -> "MEDIUM".equalsIgnoreCase(a.getDifficulty()))
                                .count();

                long hardCount = attempts.stream()
                                .filter(a -> "HARD".equalsIgnoreCase(a.getDifficulty()))
                                .count();

                return Map.of(
                                "totalUsers", totalUsers,
                                "avgScore", avgScore,
                                "easyCount", easyCount,
                                "mediumCount", mediumCount,
                                "hardCount", hardCount);
        }
}