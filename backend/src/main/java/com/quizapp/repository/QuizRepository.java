package com.quizapp.repository;

import com.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByActiveTrue();

    @EntityGraph(attributePaths = {"questions", "questions.options"})
    @Query("SELECT DISTINCT q FROM Quiz q WHERE q.id = :id")
    Optional<Quiz> findByIdWithQuestionsAndOptions(Long id);
}
