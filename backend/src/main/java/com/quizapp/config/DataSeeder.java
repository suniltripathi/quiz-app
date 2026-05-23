package com.quizapp.config;

import com.quizapp.model.*;
import com.quizapp.repository.QuizRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final QuizRepository quizRepository;

    public DataSeeder(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public void run(String... args) {
        if (quizRepository.count() > 0) {
            return;
        }

        createJavaQuiz();
        createWebDevQuiz();
        createGeneralKnowledgeQuiz();
    }

    private void createJavaQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Java Programming Fundamentals");
        quiz.setDescription("Test your knowledge of core Java concepts including OOP, collections, and more.");
        quiz.setTimeLimitMinutes(10);

        addQuestion(quiz, 0, "Which keyword is used to create a class in Java?",
                Question.QuestionType.SINGLE_CHOICE, 1,
                new String[]{"class", "Class", "struct", "define"},
                new boolean[]{true, false, false, false});

        addQuestion(quiz, 1, "What is the default value of an int variable in Java?",
                Question.QuestionType.SINGLE_CHOICE, 1,
                new String[]{"0", "1", "null", "undefined"},
                new boolean[]{true, false, false, false});

        addQuestion(quiz, 2, "Which of these are valid access modifiers in Java?",
                Question.QuestionType.MULTIPLE_CHOICE, 2,
                new String[]{"public", "private", "protected", "friend"},
                new boolean[]{true, true, true, false});

        addQuestion(quiz, 3, "Java supports multiple inheritance through classes.",
                Question.QuestionType.TRUE_FALSE, 1,
                new String[]{"True", "False"},
                new boolean[]{false, true});

        addQuestion(quiz, 4, "Which collection does not allow duplicate elements?",
                Question.QuestionType.SINGLE_CHOICE, 1,
                new String[]{"ArrayList", "LinkedList", "HashSet", "Vector"},
                new boolean[]{false, false, true, false});

        quizRepository.save(quiz);
    }

    private void createWebDevQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Web Development Essentials");
        quiz.setDescription("Challenge yourself with questions about HTML, CSS, JavaScript, and modern web technologies.");
        quiz.setTimeLimitMinutes(8);

        addQuestion(quiz, 0, "What does HTML stand for?",
                Question.QuestionType.SINGLE_CHOICE, 1,
                new String[]{"Hyper Text Markup Language", "High Tech Modern Language",
                        "Hyper Transfer Markup Language", "Home Tool Markup Language"},
                new boolean[]{true, false, false, false});

        addQuestion(quiz, 1, "Which CSS property is used to change text color?",
                Question.QuestionType.SINGLE_CHOICE, 1,
                new String[]{"font-color", "text-color", "color", "foreground-color"},
                new boolean[]{false, false, true, false});

        addQuestion(quiz, 2, "JavaScript is a statically typed language.",
                Question.QuestionType.TRUE_FALSE, 1,
                new String[]{"True", "False"},
                new boolean[]{false, true});

        addQuestion(quiz, 3, "Which of these are JavaScript frameworks/libraries?",
                Question.QuestionType.MULTIPLE_CHOICE, 2,
                new String[]{"React", "Angular", "Django", "Vue"},
                new boolean[]{true, true, false, true});

        addQuestion(quiz, 4, "What does CSS stand for?",
                Question.QuestionType.SINGLE_CHOICE, 1,
                new String[]{"Computer Style Sheets", "Cascading Style Sheets",
                        "Creative Style System", "Colorful Style Sheets"},
                new boolean[]{false, true, false, false});

        quizRepository.save(quiz);
    }

    private void createGeneralKnowledgeQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle("General Knowledge");
        quiz.setDescription("A fun mix of science, geography, history, and trivia questions.");
        quiz.setTimeLimitMinutes(15);

        addQuestion(quiz, 0, "What is the chemical symbol for gold?",
                Question.QuestionType.SINGLE_CHOICE, 1,
                new String[]{"Go", "Gd", "Au", "Ag"},
                new boolean[]{false, false, true, false});

        addQuestion(quiz, 1, "The Great Wall of China is visible from space.",
                Question.QuestionType.TRUE_FALSE, 1,
                new String[]{"True", "False"},
                new boolean[]{false, true});

        addQuestion(quiz, 2, "Which planet is known as the Red Planet?",
                Question.QuestionType.SINGLE_CHOICE, 1,
                new String[]{"Venus", "Mars", "Jupiter", "Saturn"},
                new boolean[]{false, true, false, false});

        addQuestion(quiz, 3, "Which of these are oceans?",
                Question.QuestionType.MULTIPLE_CHOICE, 2,
                new String[]{"Atlantic", "Pacific", "Mediterranean", "Indian"},
                new boolean[]{true, true, false, true});

        addQuestion(quiz, 4, "What is the largest mammal on Earth?",
                Question.QuestionType.SINGLE_CHOICE, 1,
                new String[]{"African Elephant", "Blue Whale", "Giraffe", "Polar Bear"},
                new boolean[]{false, true, false, false});

        quizRepository.save(quiz);
    }

    private void addQuestion(Quiz quiz, int order, String text, Question.QuestionType type,
                             int points, String[] optionTexts, boolean[] correctFlags) {
        Question question = new Question();
        question.setText(text);
        question.setQuestionType(type);
        question.setOrderIndex(order);
        question.setPoints(points);

        for (int i = 0; i < optionTexts.length; i++) {
            Option option = new Option();
            option.setText(optionTexts[i]);
            option.setCorrect(correctFlags[i]);
            question.addOption(option);
        }

        quiz.addQuestion(question);
    }
}
