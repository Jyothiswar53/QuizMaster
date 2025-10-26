package com.quiz_app.quiz_app.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuizSubmissionDto {
    private String difficulty;
    private List<Long> questionIds;
    private List<String> answers;
}