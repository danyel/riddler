package be.riddler.v1.activity.api;

import be.riddler.common.path.Paths;
import be.riddler.common.resource.AbstractResourceIntegrationTest;
import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.CreateActivity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static be.riddler.v1.fixture.Fixture.Question.DB_ID;
import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * ActivityResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Activity Resource")
class ActivityResourceIntegrationTest extends AbstractResourceIntegrationTest {

    @DisplayName("Given we have added an activity when fetching the activity then activityDetail will be return")
    @Test
    void getActivities() {
        var instant = now().toInstant(UTC);
        var actionType = "action";
        var elementId = "element";
        var additionalData = "additional data";
        var createActivity = new CreateActivity(DB_ID, actionType, elementId, instant, additionalData);

        var activityDetailCreated = doExchange(getWebTestClient().post()
                .uri(Paths.CATEGORY)
                .bodyValue(createActivity))
                .expectStatus()
                .isCreated()
                .expectBody(ActivityDetail.class)
                .returnResult()
                .getResponseBody();

        assert activityDetailCreated != null;

        var activityDetails = doExchange(getWebTestClient().get()
                .uri("%s/%s".formatted(Paths.CATEGORY, DB_ID)))
                .expectStatus()
                .isOk()
                .expectBody(ActivityDetail[].class)
                .returnResult()
                .getResponseBody();

        assertNotNull(activityDetails);
        var activityDetail = activityDetails[0];

        assertEquals(actionType, activityDetail.actionType());
        assertEquals(elementId, activityDetail.elementId());
        assertEquals(additionalData, activityDetail.additionalData());
    }
}