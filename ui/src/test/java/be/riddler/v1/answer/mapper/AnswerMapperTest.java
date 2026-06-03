package be.riddler.v1.answer.mapper;

import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.client.model.CreateSolution;
import be.riddler.v1.fixture.Fixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * AnswerMapperTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Answer Mapper")
class AnswerMapperTest {

    @DisplayName("Mapping the create answer to answer entity")
    @Test
    void fromCreateAnswer() {
        var answerEntity = AnswerMapper.fromCreateAnswer(new CreateAnswer(List.of(), Fixture.Question.QUESTION_ID, Fixture.Invitation.INVITATION_ID));
        assertNotNull(answerEntity);
        assertEquals(Fixture.Question.QUESTION_ID, answerEntity.getQuestionId());
        assertEquals(Fixture.Invitation.INVITATION_ID, answerEntity.getInvitationId());
        Assertions.assertThat(answerEntity.getSolutions()).isEmpty();


        answerEntity = AnswerMapper.fromCreateAnswer(new CreateAnswer(List.of(new CreateSolution("value")), Fixture.Question.QUESTION_ID, Fixture.Invitation.INVITATION_ID));
        assertNotNull(answerEntity);
        assertEquals(Fixture.Question.QUESTION_ID, answerEntity.getQuestionId());
        assertEquals(Fixture.Invitation.INVITATION_ID, answerEntity.getInvitationId());
        Assertions.assertThat(answerEntity.getSolutions()).isEmpty();
    }

    @DisplayName("Mapping answer entity to answer")
    @Test
    void fromAnswerEntity() {
        var answer = AnswerMapper.fromAnswerEntity(Fixture.Answer.ANSWER_ENTITY);
        assertNotNull(answer);
        assertEquals(Fixture.Question.QUESTION_ID, answer.questionId());
        assertEquals(Fixture.Invitation.INVITATION_ID, answer.invitationId());
        assertEquals(Fixture.Answer.ANSWER_ID, answer.id());
        Assertions.assertThat(answer.solutions()).isEmpty();

        var answerEntity = Fixture.Answer.ANSWER_ENTITY;
        answerEntity.setSolutions(List.of(Fixture.Solution.SOLUTION_ENTITY));
        answer = AnswerMapper.fromAnswerEntity(answerEntity);
        assertNotNull(answer);
        assertEquals(Fixture.Question.QUESTION_ID, answer.questionId());
        assertEquals(Fixture.Invitation.INVITATION_ID, answer.invitationId());
        assertEquals(Fixture.Answer.ANSWER_ID, answer.id());
        Assertions.assertThat(answer.solutions()).hasSize(1);
        assertEquals(Fixture.Solution.SOLUTION_ID, answer.solutions().getFirst().id());
        assertEquals("value", answer.solutions().getFirst().value());
    }
}