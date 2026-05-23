package com.quizapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:mysql://localhost:3306/quiz_app?useSSL=false&allowPublicKeyRetrieval=true",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class QuizApplicationTests {

    @Test
    void contextLoads() {
    }
}
