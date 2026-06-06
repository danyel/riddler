package be.riddler.v1.answer.repository;

import be.riddler.common.repository.AbstractRepositoryIntegrationTest;
import be.riddler.v1.answer.entity.AnswerEntity;
import be.riddler.v1.answer.entity.SolutionEntity;
import be.riddler.v1.question.client.model.QuestionType;
import be.riddler.v1.question.entity.QuestionEntity;
import be.riddler.v1.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static be.riddler.v1.fixture.Fixture.Invitation.INVITATION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * SolutionRepositoryTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Solution Repository")
class SolutionRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    @Test
    void addOption() {
        var questionEntity = questionRepository.save(QuestionEntity.builder()
                .question("Question")
                .title("Title")
                .type(QuestionType.MULTIPLE_CHOICE)
                .build());
        var answerEntity = answerRepository.save(AnswerEntity.builder()
                .questionId(questionEntity.getId())
                .invitationId(INVITATION_ID)
                .build());
        var solutionEntity = solutionRepository.save(SolutionEntity.builder()
                .value("value")
                .answer(answerEntity)
                .build());

        assertNotNull(solutionEntity.getId());
        assertEquals("value", solutionEntity.getValue());

        solutionRepository.delete(solutionEntity);
        answerRepository.delete(answerEntity);
        questionRepository.delete(questionEntity);
        assertFalse(solutionRepository.existsById(solutionEntity.getId()));
        assertFalse(answerRepository.existsById(answerEntity.getId()));
        assertFalse(questionRepository.existsById(questionEntity.getId()));
    }

    @Transactional
    @Test
    void updateOption() {
        var questionEntity = questionRepository.save(QuestionEntity.builder()
                .question("Question")
                .title("Title")
                .type(QuestionType.MULTIPLE_CHOICE)
                .build());
        var answerEntity = answerRepository.save(AnswerEntity.builder()
                .questionId(questionEntity.getId())
                .invitationId(INVITATION_ID)
                .build());
        var solutionEntity = solutionRepository.save(SolutionEntity.builder()
                .value("value")
                .answer(answerEntity)
                .build());

        assertNotNull(solutionEntity.getId());
        assertEquals("value", solutionEntity.getValue());

        solutionEntity.setValue("newValue");
        solutionRepository.save(solutionEntity);
        assertEquals("newValue", solutionEntity.getValue());

        solutionRepository.delete(solutionEntity);
        answerRepository.delete(answerEntity);
        questionRepository.delete(questionEntity);
        assertFalse(solutionRepository.existsById(solutionEntity.getId()));
        assertFalse(answerRepository.existsById(answerEntity.getId()));
        assertFalse(questionRepository.existsById(questionEntity.getId()));
    }
}