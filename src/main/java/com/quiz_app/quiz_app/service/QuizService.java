package com.quiz_app.quiz_app.service;

import com.quiz_app.quiz_app.dto.QuizSubmissionDto;
import com.quiz_app.quiz_app.model.Question;
import com.quiz_app.quiz_app.model.QuizAttempt;
import com.quiz_app.quiz_app.model.User;
import com.quiz_app.quiz_app.repository.QuestionRepository;
import com.quiz_app.quiz_app.repository.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    public List<Question> getQuestions(String difficulty, int limit) {
        List<Question> questions = questionRepository.findByDifficulty(difficulty);

        if (questions.size() > limit) {
            Collections.shuffle(questions);
            return questions.subList(0, limit);
        }

        return questions;
    }

    public QuizAttempt submitQuiz(User user, QuizSubmissionDto submission) {
        int score = 0;

        List<Question> questions = questionRepository.findAllById(submission.getQuestionIds());

        Map<Long, Question> questionMap = new HashMap<>();
        for (Question q : questions) {
            questionMap.put(q.getId(), q);
        }

        for (int i = 0; i < submission.getQuestionIds().size(); i++) {
            Long qid = submission.getQuestionIds().get(i);
            String userAnswer = submission.getAnswers().get(i);

            Question q = questionMap.get(qid);
            if (q == null)
                continue;

            String correct = q.getCorrectAnswer();

            String userTrim = (userAnswer != null) ? userAnswer.trim() : "";
            String correctTrim = (correct != null) ? correct.trim() : "";

            if (userTrim.equalsIgnoreCase(correctTrim)) {
                score++;
            }
        }

        QuizAttempt attempt = new QuizAttempt();
        attempt.setUser(user);
        attempt.setDifficulty(submission.getDifficulty());
        attempt.setScore(score);
        attempt.setTotalQuestions(submission.getQuestionIds().size());
        attempt.setDateTaken(LocalDateTime.now());

        return quizAttemptRepository.save(attempt);
    }

    public QuizAttempt getAttemptById(Long id) {
        return quizAttemptRepository.findById(id).orElse(null);
    }

    public List<QuizAttempt> getAllAttempts() {
        return quizAttemptRepository.findAll();
    }

    public QuizAttempt getLatestAttempt(User user) {
        return quizAttemptRepository.findTopByUserOrderByDateTakenDesc(user).orElse(null);
    }
}