// File: src/main/java/com/quiz_app/quiz_app/repository/QuizAttemptRepository.java

package com.quiz_app.quiz_app.repository;

import com.quiz_app.quiz_app.model.QuizAttempt;
import com.quiz_app.quiz_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    List<QuizAttempt> findByDifficultyOrderByScoreDesc(String difficulty);

    List<QuizAttempt> findByUserId(Long userId);

    Optional<QuizAttempt> findTopByUserOrderByDateTakenDesc(User user);

    @Query("SELECT q.user.username, MAX(q.score) " +
            "FROM QuizAttempt q " +
            "WHERE q.difficulty = :difficulty " +
            "GROUP BY q.user.username " +
            "ORDER BY MAX(q.score) DESC")
    List<Object[]> findBestScoresByDifficulty(String difficulty);
}