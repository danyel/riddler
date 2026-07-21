package be.riddler.v1.activity.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.CreateActivity;
import be.riddler.v1.activity.entity.ActivityEntity;
import be.riddler.v1.activity.mapper.ActivityMapper;
import be.riddler.v1.activity.repository.ActivityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static be.riddler.v1.fixture.Fixture.Question.QUESTION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * CreateActivityFeatureImplTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Create Activity Feature")
class CreateActivityFeatureTest extends AbstractMockTest {
    @Mock
    private ActivityRepository activityRepository;
    @InjectMocks
    private CreateActivityFeatureImpl ut;

    @DisplayName("Given an action taken when creating the activity then activity saved and returned")
    @Test
    void create() {
        try (MockedStatic<ActivityMapper> activityMapper = mockStatic(ActivityMapper.class)) {
            var instant = LocalDateTime.now().toInstant(ZoneOffset.UTC);
            var createActivity = new CreateActivity(QUESTION_ID, "action", "element", instant, "nothing");
            var activityEntity = ActivityEntity.builder()
                    .actionType(createActivity.actionType())
                    .questionId(createActivity.questionId())
                    .timestamp(createActivity.timestamp())
                    .elementId(createActivity.elementId())
                    .additionalData(createActivity.additionalData())
                    .build();
            activityMapper.when(() -> ActivityMapper.fromActivityEntity(activityEntity)).thenReturn(new ActivityDetail(UUID.randomUUID(), createActivity.questionId(), createActivity.actionType(), createActivity.elementId(), createActivity.timestamp(), createActivity.additionalData()));
            activityMapper.when(() -> ActivityMapper.fromCreateActivity(createActivity)).thenReturn(activityEntity);


            ut.create(createActivity);

            verify(activityRepository).save(eq(activityEntity));
            verifyNoMoreInteractions(activityRepository);
            assertEquals(QUESTION_ID, activityEntity.getQuestionId());
            assertEquals("action", activityEntity.getActionType());
            assertEquals("nothing", activityEntity.getAdditionalData());
            assertEquals("element", activityEntity.getElementId());
            assertEquals(instant, activityEntity.getTimestamp());
        }
    }
}