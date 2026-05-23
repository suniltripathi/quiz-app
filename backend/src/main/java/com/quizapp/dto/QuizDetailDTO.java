package com.quizapp.dto;

import com.quizapp.model.Quiz;

import java.util.List;
import java.util.stream.Collectors;

public class QuizDetailDTO {

    private Long id;
    private String title;
    private String description;
    private Integer timeLimitMinutes;
    private boolean active;
    private List<QuestionDTO> questions;

    public QuizDetailDTO() {}

    public QuizDetailDTO(Quiz quiz, boolean includeCorrectAnswers) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.description = quiz.getDescription();
        this.timeLimitMinutes = quiz.getTimeLimitMinutes();
        this.active = quiz.isActive();
        this.questions = quiz.getQuestions().stream()
                .map(q -> new QuestionDTO(q, includeCorrectAnswers))
                .collect(Collectors.toList());
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

    public List<QuestionDTO> getQuestions() { return questions; }
    public void setQuestions(List<QuestionDTO> questions) { this.questions = questions; }
}
