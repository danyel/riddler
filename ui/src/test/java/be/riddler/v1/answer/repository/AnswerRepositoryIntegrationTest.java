package be.riddler.v1.answer.repository;

import be.riddler.common.repository.AbstractRepositoryIntegrationTest;
import be.riddler.v1.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * AnswerRepositoryTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@DisplayName("Answer Repository")
class AnswerRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void findAllByQuestionId() {
        assertEquals(3, answerRepository.findAllByQuestionId(Fixture.Question.DB_ID).size());
    }
}