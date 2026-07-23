package be.riddler.v1.answer.api;

import be.riddler.common.path.Paths;
import be.riddler.common.resource.AbstractResourceIntegrationTest;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.client.model.CreateSolution;
import be.riddler.v1.answer.client.model.UpdateAnswer;
import be.riddler.v1.answer.client.model.UpdateSolution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.riddler.v1.fixture.Fixture.Answer.ANSWER_ID;
import static be.riddler.v1.fixture.Fixture.Invitation.INVITATION_ID;
import static be.riddler.v1.fixture.Fixture.Question.DB_ID;
import static be.riddler.v1.fixture.Fixture.Solution.SOLUTION_ID;

/**
 * AnswerResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@DisplayName("Answer Resource")
class AnswerResourceIntegrationTest extends AbstractResourceIntegrationTest {


    @DisplayName("Given a valid question id when retrieving answers for that question then a list of answers will be returned")
    @Test
    void findByQuestionId() {
        doExchange(getWebTestClient().get()
                .uri("%s/question/%s".formatted(Paths.ANSWER, DB_ID)))
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].id")
                .isEqualTo("a9e8cbaa-e047-41bb-9554-d4b08352a08a")
                .jsonPath("$[1].id")
                .isEqualTo("18adc354-d7ea-4f97-a579-5628f3cfa0cb")
                .jsonPath("$[2].id")
                .isEqualTo("a599e7e2-2e67-4638-9638-d47e82f909d1");
    }

    @DisplayName("Given a valid create answer when trying to create the answer and id will be returned")
    @Test
    void create() {
        var createAnswer = new CreateAnswer(
                List.of(new CreateSolution("Solution")),
                DB_ID,
                INVITATION_ID
        );
        doExchange(getWebTestClient().post()
                .uri(Paths.ANSWER)
                .bodyValue(createAnswer))
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.invitation_id")
                .isEqualTo(INVITATION_ID);
    }

    @DisplayName("Given a invalid answer to update when updating it then 404 returned")
    @Test
    void update() {
        doExchange(getWebTestClient().put()
                .uri("%s/%s".formatted(Paths.ANSWER, ANSWER_ID))
                .bodyValue(new UpdateAnswer(List.of(new UpdateSolution(SOLUTION_ID, "another solution")))))
                .expectStatus()
                .isNotFound();
    }
}