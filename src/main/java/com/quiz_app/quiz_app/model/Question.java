package com.quiz_app.quiz_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "questions")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
    private String difficulty;
}