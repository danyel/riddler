package be.riddler.v1.answer.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.client.model.CreateSolution;
import be.riddler.v1.answer.client.model.Solution;
import be.riddler.v1.answer.entity.AnswerEntity;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.mapper.SolutionMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.answer.repository.SolutionRepository;
import be.riddler.v1.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.List;

import static be.riddler.v1.fixture.Fixture.Answer.ANSWER_ID;
import static be.riddler.v1.fixture.Fixture.Invitation.INVITATION_ID;
import static be.riddler.v1.fixture.Fixture.Question.QUESTION_ID;
import static be.riddler.v1.fixture.Fixture.Solution.SOLUTION_ENTITY;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mockStatic;

/**
 * CreateAnswerFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Create Answer Feature")
class CreateAnswerFeatureTest extends AbstractMockTest {
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private SolutionRepository solutionRepository;
    @InjectMocks
    private CreateAnswerFeatureImpl ut;

    /**
     * Given:
     * - valid create answer with one solution
     * When:
     * Creating an answer for a question id (11111111-1111-1111-1111-111111111111)
     * Then:
     * - saves first the answer
     * - solutions will be saved
     */
    @DisplayName("Given a create answer where we want to add when the answer will be saves")
    @Test
    void create() {
        try (MockedStatic<AnswerMapper> answerMapper = mockStatic(AnswerMapper.class)) {
            try (MockedStatic<SolutionMapper> solutionMapper = mockStatic(SolutionMapper.class)) {
                var createAnswer = new CreateAnswer(List.of(new CreateSolution("value")), QUESTION_ID, INVITATION_ID);
                var answerEntity = AnswerEntity.builder()
                        .questionId(QUESTION_ID)
                        .build();
                answerMapper.when(() -> AnswerMapper.fromCreateAnswer(eq(createAnswer))).thenReturn(answerEntity);
                solutionMapper.when(() -> SolutionMapper.fromCreateSolutionToEntity(eq(new CreateSolution("value")), eq(answerEntity)))
                        .thenReturn(SOLUTION_ENTITY);
                answerMapper.when(() -> AnswerMapper.fromAnswerEntity(eq(Fixture.Answer.ANSWER_ENTITY)))
                        .thenReturn(new Answer(ANSWER_ID, INVITATION_ID, List.of(new Solution(SOLUTION_ENTITY.getId(), SOLUTION_ENTITY.getValue())), QUESTION_ID));

                ut.create(createAnswer);

                var inOrder = inOrder(solutionRepository, answerRepository);
                inOrder.verify(answerRepository).save(answerEntity);
                inOrder.verify(solutionRepository).save(SOLUTION_ENTITY);
                inOrder.verify(answerRepository).save(answerEntity);
                inOrder.verifyNoMoreInteractions();
            }
        }
    }
}