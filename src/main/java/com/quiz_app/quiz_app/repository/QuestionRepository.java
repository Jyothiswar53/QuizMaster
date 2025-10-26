package com.quiz_app.quiz_app.repository;

import com.quiz_app.quiz_app.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDifficulty(String difficulty);
}