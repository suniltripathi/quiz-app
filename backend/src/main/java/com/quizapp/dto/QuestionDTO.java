package com.quizapp.dto;

import com.quizapp.model.Question;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionDTO {

    private Long id;
    private String text;
    private String questionType;
    private Integer orderIndex;
    private Integer points;
    private List<OptionDTO> options;

    public QuestionDTO() {}

    public QuestionDTO(Question question, boolean includeCorrectAnswers) {
        this.id = question.getId();
        this.text = question.getText();
        this.questionType = question.getQuestionType().name();
        this.orderIndex = question.getOrderIndex();
        this.points = question.getPoints();
        this.options = question.getOptions().stream()
                .map(o -> new OptionDTO(o, includeCorrectAnswers))
                .collect(Collectors.toList());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getQuestionType() { return questionType; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public List<OptionDTO> getOptions() { return options; }
    public void setOptions(List<OptionDTO> options) { this.options = options; }
}
