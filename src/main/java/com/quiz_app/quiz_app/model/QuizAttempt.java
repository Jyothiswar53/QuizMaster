package com.quiz_app.quiz_app.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_attempts")
@Data
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String difficulty;
    private int score;
    private int totalQuestions;
    private LocalDateTime dateTaken;

    @OneToMany(mappedBy = "quizAttempt", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<QuizQuestionAttempt> questionAttempts;
}
