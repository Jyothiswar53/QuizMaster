package com.quiz_app.quiz_app.service;

import com.quiz_app.quiz_app.dto.QuizSubmissionDto;
import com.quiz_app.quiz_app.model.Question;
import com.quiz_app.quiz_app.model.QuizAttempt;
import com.quiz_app.quiz_app.model.QuizQuestionAttempt;
import com.quiz_app.quiz_app.model.User;
import com.quiz_app.quiz_app.repository.QuestionRepository;
import com.quiz_app.quiz_app.repository.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
        for (Question q : questions) questionMap.put(q.getId(), q);

        List<QuizQuestionAttempt> questionAttempts = new ArrayList<>();

        for (int i = 0; i < submission.getQuestionIds().size(); i++) {
            Long qid = submission.getQuestionIds().get(i);
            String userAnswer = submission.getAnswers().get(i);
            Question q = questionMap.get(qid);
            if (q == null) continue;

            String correct = q.getCorrectAnswer();
            boolean correctFlag = false;
            if (userAnswer != null && correct != null &&
                    userAnswer.trim().equalsIgnoreCase(correct.trim())) {
                score++;
                correctFlag = true;
            }

            QuizQuestionAttempt qa = new QuizQuestionAttempt();
            qa.setQuestionText(q.getQuestionText());
            qa.setOption1(q.getOption1());
            qa.setOption2(q.getOption2());
            qa.setOption3(q.getOption3());
            qa.setOption4(q.getOption4());
            qa.setCorrectAnswer(correct);
            qa.setUserAnswer(userAnswer);
            qa.setCorrect(correctFlag);
            questionAttempts.add(qa);
        }

        QuizAttempt attempt = new QuizAttempt();
        attempt.setUser(user);
        attempt.setDifficulty(submission.getDifficulty());
        attempt.setScore(score);
        attempt.setTotalQuestions(submission.getQuestionIds().size());
        attempt.setDateTaken(LocalDateTime.now());

        for (QuizQuestionAttempt qa : questionAttempts) {
            qa.setQuizAttempt(attempt);
        }
        attempt.setQuestionAttempts(questionAttempts);

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
