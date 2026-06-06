package be.riddler.v1.answer.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.answer.client.model.QuestionId;
import be.riddler.v1.answer.entity.AnswerEntity;
import be.riddler.v1.answer.repository.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * GetAnswersByQuestionIdFeatureImplTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Get Answers By Question Id Feature")
class GetAnswersByQuestionIdFeatureTest extends AbstractMockTest {
    @Mock
    private AnswerRepository answerRepository;
    @InjectMocks
    private GetAnswersByQuestionIdFeatureImpl ut;
    private final UUID questionId = UUID.fromString("11111111-1111-1111-1111-111111111111");

    /**
     * Given:
     * - valid question id (11111111-1111-1111-1111-111111111111)
     * When:
     * Fetching answers for the question id
     * Then:
     * - two answers found for the given question id
     */
    @DisplayName("Given a valid question id when fetching answers for the question id then 2 answers will be returned")
    @Test
    void findById() {
        when(answerRepository.findAllByQuestionId(questionId)).thenReturn(of(
                        AnswerEntity.builder()
                                .questionId(questionId)
                                .build(),
                        AnswerEntity.builder()
                                .questionId(questionId)
                                .build()
                )
        );

        var answers = ut.byQuestionId(new QuestionId(questionId));

        assertEquals(2, answers.size());
    }

    /**
     * Given:
     * - valid question id (11111111-1111-1111-1111-111111111111)
     * When:
     * Fetching answers for the question id
     * Then:
     * - no answers found
     */
    @DisplayName("Given a question id when fetching the answers then an empty list will be returned")
    @Test
    void findByIdNothingFound() {
        when(answerRepository.findAllByQuestionId(questionId)).thenReturn(of());

        var answers = ut.byQuestionId(new QuestionId(questionId));

        assertEquals(0, answers.size());
    }
}