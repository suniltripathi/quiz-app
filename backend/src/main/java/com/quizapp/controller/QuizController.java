package com.quizapp.controller;

import com.quizapp.dto.*;
import com.quizapp.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<List<QuizSummaryDTO>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllActiveQuizzes());
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuizSummaryDTO>> getAllQuizzesIncludingInactive() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDetailDTO> getQuiz(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(quizService.getQuizForTaking(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/admin")
    public ResponseEntity<QuizDetailDTO> getQuizWithAnswers(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(quizService.getQuizWithAnswers(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<QuizDetailDTO> createQuiz(@Valid @RequestBody CreateQuizRequest request) {
        QuizDetailDTO created = quizService.createQuiz(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        try {
            quizService.deleteQuiz(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<QuizResultDTO> submitQuiz(
            @PathVariable Long id,
            @Valid @RequestBody SubmitQuizRequest request) {
        try {
            QuizResultDTO result = quizService.submitQuiz(id, request);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
    }
}
