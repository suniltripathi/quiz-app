package com.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class CreateQuizRequest {

    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private Integer timeLimitMinutes;

    @NotEmpty(message = "At least one question is required")
    private List<CreateQuestionRequest> questions;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getTimeLimitMinutes() { return timeLimitMinutes; }
    public void setTimeLimitMinutes(Integer timeLimitMinutes) { this.timeLimitMinutes = timeLimitMinutes; }

    public List<CreateQuestionRequest> getQuestions() { return questions; }
    public void setQuestions(List<CreateQuestionRequest> questions) { this.questions = questions; }

    public static class CreateQuestionRequest {
        @NotBlank(message = "Question text is required")
        private String text;
        private String questionType = "SINGLE_CHOICE";
        private Integer points = 1;

        @NotEmpty(message = "At least two options are required")
        private List<CreateOptionRequest> options;

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public String getQuestionType() { return questionType; }
        public void setQuestionType(String questionType) { this.questionType = questionType; }

        public Integer getPoints() { return points; }
        public void setPoints(Integer points) { this.points = points; }

        public List<CreateOptionRequest> getOptions() { return options; }
        public void setOptions(List<CreateOptionRequest> options) { this.options = options; }
    }

    public static class CreateOptionRequest {
        @NotBlank(message = "Option text is required")
        private String text;
        private boolean correct = false;

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public boolean isCorrect() { return correct; }
        public void setCorrect(boolean correct) { this.correct = correct; }
    }
}
