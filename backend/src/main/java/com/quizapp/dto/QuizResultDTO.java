package com.quizapp.dto;

import java.util.List;

public class QuizResultDTO {

    private Long attemptId;
    private String playerName;
    private String quizTitle;
    private int totalQuestions;
    private int correctAnswers;
    private int totalPoints;
    private int earnedPoints;
    private double percentage;
    private Integer timeTakenSeconds;
    private List<QuestionResultDTO> questionResults;

    public Long getAttemptId() { return attemptId; }
    public void setAttemptId(Long attemptId) { this.attemptId = attemptId; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getQuizTitle() { return quizTitle; }
    public void setQuizTitle(String quizTitle) { this.quizTitle = quizTitle; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }

    public int getEarnedPoints() { return earnedPoints; }
    public void setEarnedPoints(int earnedPoints) { this.earnedPoints = earnedPoints; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public Integer getTimeTakenSeconds() { return timeTakenSeconds; }
    public void setTimeTakenSeconds(Integer timeTakenSeconds) { this.timeTakenSeconds = timeTakenSeconds; }

    public List<QuestionResultDTO> getQuestionResults() { return questionResults; }
    public void setQuestionResults(List<QuestionResultDTO> questionResults) { this.questionResults = questionResults; }

    public static class QuestionResultDTO {
        private Long questionId;
        private String questionText;
        private boolean correct;
        private List<String> selectedAnswers;
        private List<String> correctAnswers;
        private int pointsEarned;

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }

        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }

        public boolean isCorrect() { return correct; }
        public void setCorrect(boolean correct) { this.correct = correct; }

        public List<String> getSelectedAnswers() { return selectedAnswers; }
        public void setSelectedAnswers(List<String> selectedAnswers) { this.selectedAnswers = selectedAnswers; }

        public List<String> getCorrectAnswers() { return correctAnswers; }
        public void setCorrectAnswers(List<String> correctAnswers) { this.correctAnswers = correctAnswers; }

        public int getPointsEarned() { return pointsEarned; }
        public void setPointsEarned(int pointsEarned) { this.pointsEarned = pointsEarned; }
    }
}
