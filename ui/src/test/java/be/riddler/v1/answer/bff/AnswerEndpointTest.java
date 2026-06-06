package be.riddler.v1.answer.bff;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.answer.client.AnswerClient;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.client.model.UpdateAnswer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static be.riddler.v1.fixture.Fixture.Answer.ANSWER_ID;
import static be.riddler.v1.fixture.Fixture.Question.QUESTION_ID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * AnswerEndpointTest
 *
 * @author dnoulet
 * @version 1.0.0 06/06/2026
 */
@DisplayName("Answer Endpoint Test")
class AnswerEndpointTest extends AbstractMockTest {
    @Mock
    private AnswerClient answerClient;
    @InjectMocks
    private AnswerEndpoint ut;

    @DisplayName("Find By Question")
    @Test
    void findByQuestion() {
        ut.findByQuestion(QUESTION_ID);

        verify(answerClient).findByQuestionId(QUESTION_ID);
    }

    @DisplayName("Creat Answer")
    @Test
    void create() {
        var createAnswer = mock(CreateAnswer.class);
        ut.create(createAnswer);
        verify(answerClient).create(createAnswer);
    }

    @DisplayName("Update Answer")
    @Test
    void update() {
        var updateAnswer = mock(UpdateAnswer.class);
        ut.update(ANSWER_ID, updateAnswer);
        verify(answerClient).update(ANSWER_ID, updateAnswer);
    }
}