package com.quiz_app.quiz_app.repository;

import com.quiz_app.quiz_app.model.QuizQuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizQuestionAttemptRepository extends JpaRepository<QuizQuestionAttempt, Long> {
    List<QuizQuestionAttempt> findByQuizAttemptId(Long attemptId);
}
