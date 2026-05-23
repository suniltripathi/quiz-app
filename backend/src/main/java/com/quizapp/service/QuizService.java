package com.quizapp.service;

import com.quizapp.dto.*;
import com.quizapp.model.*;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.QuizAttemptRepository;
import com.quizapp.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    public QuizService(QuizRepository quizRepository,
                       QuestionRepository questionRepository,
                       QuizAttemptRepository quizAttemptRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    public List<QuizSummaryDTO> getAllActiveQuizzes() {
        return quizRepository.findByActiveTrue().stream()
                .map(QuizSummaryDTO::new)
                .collect(Collectors.toList());
    }

    public List<QuizSummaryDTO> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(QuizSummaryDTO::new)
                .collect(Collectors.toList());
    }

    public QuizDetailDTO getQuizForTaking(Long quizId) {
        Quiz quiz = quizRepository.findByIdWithQuestionsAndOptions(quizId)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found with id: " + quizId));
        return new QuizDetailDTO(quiz, false);
    }

    public QuizDetailDTO getQuizWithAnswers(Long quizId) {
        Quiz quiz = quizRepository.findByIdWithQuestionsAndOptions(quizId)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found with id: " + quizId));
        return new QuizDetailDTO(quiz, true);
    }

    public QuizDetailDTO createQuiz(CreateQuizRequest request) {
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimitMinutes(request.getTimeLimitMinutes());

        for (int i = 0; i < request.getQuestions().size(); i++) {
            CreateQuizRequest.CreateQuestionRequest qReq = request.getQuestions().get(i);
            Question question = new Question();
            question.setText(qReq.getText());
            question.setQuestionType(Question.QuestionType.valueOf(qReq.getQuestionType()));
            question.setPoints(qReq.getPoints() != null ? qReq.getPoints() : 1);
            question.setOrderIndex(i);

            for (CreateQuizRequest.CreateOptionRequest oReq : qReq.getOptions()) {
                Option option = new Option();
                option.setText(oReq.getText());
                option.setCorrect(oReq.isCorrect());
                question.addOption(option);
            }

            quiz.addQuestion(question);
        }

        Quiz saved = quizRepository.save(quiz);
        return new QuizDetailDTO(saved, true);
    }

    public void deleteQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new NoSuchElementException("Quiz not found with id: " + quizId);
        }
        quizRepository.deleteById(quizId);
    }

    public QuizResultDTO submitQuiz(Long quizId, SubmitQuizRequest request) {
        Quiz quiz = quizRepository.findByIdWithQuestionsAndOptions(quizId)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found with id: " + quizId));

        Map<Long, List<Long>> answerMap = new HashMap<>();
        for (SubmitQuizRequest.AnswerRequest answer : request.getAnswers()) {
            answerMap.put(answer.getQuestionId(),
                    answer.getSelectedOptionIds() != null ? answer.getSelectedOptionIds() : Collections.emptyList());
        }

        int correctCount = 0;
        int totalPoints = 0;
        int earnedPoints = 0;
        List<QuizResultDTO.QuestionResultDTO> questionResults = new ArrayList<>();

        for (Question question : quiz.getQuestions()) {
            totalPoints += question.getPoints();
            List<Long> selectedIds = answerMap.getOrDefault(question.getId(), Collections.emptyList());

            Set<Long> correctOptionIds = question.getOptions().stream()
                    .filter(Option::isCorrect)
                    .map(Option::getId)
                    .collect(Collectors.toSet());

            Set<Long> selectedSet = new HashSet<>(selectedIds);
            boolean isCorrect = correctOptionIds.equals(selectedSet);

            if (isCorrect) {
                correctCount++;
                earnedPoints += question.getPoints();
            }

            QuizResultDTO.QuestionResultDTO qResult = new QuizResultDTO.QuestionResultDTO();
            qResult.setQuestionId(question.getId());
            qResult.setQuestionText(question.getText());
            qResult.setCorrect(isCorrect);
            qResult.setPointsEarned(isCorrect ? question.getPoints() : 0);
            qResult.setSelectedAnswers(question.getOptions().stream()
                    .filter(o -> selectedSet.contains(o.getId()))
                    .map(Option::getText)
                    .collect(Collectors.toList()));
            qResult.setCorrectAnswers(question.getOptions().stream()
                    .filter(Option::isCorrect)
                    .map(Option::getText)
                    .collect(Collectors.toList()));
            questionResults.add(qResult);
        }

        QuizAttempt attempt = new QuizAttempt();
        attempt.setPlayerName(request.getPlayerName());
        attempt.setQuiz(quiz);
        attempt.setScore(correctCount);
        attempt.setTotalQuestions(quiz.getQuestions().size());
        attempt.setTotalPoints(totalPoints);
        attempt.setEarnedPoints(earnedPoints);
        attempt.setTimeTakenSeconds(request.getTimeTakenSeconds());
        quizAttemptRepository.save(attempt);

        QuizResultDTO result = new QuizResultDTO();
        result.setAttemptId(attempt.getId());
        result.setPlayerName(request.getPlayerName());
        result.setQuizTitle(quiz.getTitle());
        result.setTotalQuestions(quiz.getQuestions().size());
        result.setCorrectAnswers(correctCount);
        result.setTotalPoints(totalPoints);
        result.setEarnedPoints(earnedPoints);
        result.setPercentage(totalPoints > 0 ? (double) earnedPoints / totalPoints * 100 : 0);
        result.setTimeTakenSeconds(request.getTimeTakenSeconds());
        result.setQuestionResults(questionResults);

        return result;
    }

    public List<QuizAttempt> getLeaderboard(Long quizId) {
        return quizAttemptRepository.findByQuizIdOrderByEarnedPointsDesc(quizId);
    }
}
