package com.quizapp.dto;

import com.quizapp.model.Option;

public class OptionDTO {

    private Long id;
    private String text;
    private Boolean correct;

    public OptionDTO() {}

    public OptionDTO(Option option, boolean includeCorrectAnswer) {
        this.id = option.getId();
        this.text = option.getText();
        this.correct = includeCorrectAnswer ? option.isCorrect() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Boolean getCorrect() { return correct; }
    public void setCorrect(Boolean correct) { this.correct = correct; }
}
