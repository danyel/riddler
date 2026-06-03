package be.riddler.v1.question.repository;

import be.riddler.common.repository.AbstractRepositoryIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static be.riddler.v1.fixture.Fixture.Question.DB_ID;
import static be.riddler.v1.question.client.model.QuestionType.MULTIPLE_CHOICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * QuestionRepositoryTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@DisplayName("Question Repository")
class QuestionRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void findAll() {
        var questions = questionRepository.findAll();
        assertEquals(5, questions.size());
    }

    @Test
    void findById() {
        var question = questionRepository.findById(DB_ID);
        assertTrue(question.isPresent());
        assertEquals(MULTIPLE_CHOICE, question.get().getType());
        assertEquals("Question 3", question.get().getQuestion());
    }
}