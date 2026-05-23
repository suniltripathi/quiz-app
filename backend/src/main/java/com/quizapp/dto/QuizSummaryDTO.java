package com.quizapp.dto;

import com.quizapp.model.Quiz;

public class QuizSummaryDTO {

    private Long id;
    private String title;
    private String description;
    private Integer timeLimitMinutes;
    private boolean active;
    private int questionCount;

    public QuizSummaryDTO() {}

    public QuizSummaryDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.description = quiz.getDescription();
        this.timeLimitMinutes = quiz.getTimeLimitMinutes();
        this.active = quiz.isActive();
        this.questionCount = quiz.getQuestions().size();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getTimeLimitMinutes() { return timeLimitMinutes; }
    public void setTimeLimitMinutes(Integer timeLimitMinutes) { this.timeLimitMinutes = timeLimitMinutes; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getQuestionCount() { return questionCount; }
    public void setQuestionCount(int questionCount) { this.questionCount = questionCount; }
}
