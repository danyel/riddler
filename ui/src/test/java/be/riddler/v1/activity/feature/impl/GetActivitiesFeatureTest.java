package be.riddler.v1.activity.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.GetActivities;
import be.riddler.v1.activity.entity.ActivityEntity;
import be.riddler.v1.activity.mapper.ActivityMapper;
import be.riddler.v1.activity.repository.ActivityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.List;

import static be.riddler.v1.fixture.Fixture.Question.QUESTION_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * GetActivitiesFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Get Activities Feature")
class GetActivitiesFeatureTest extends AbstractMockTest {
    @Mock
    private ActivityRepository activityRepository;
    @InjectMocks
    private GetActivitiesFeatureImpl ut;

    /**
     * When:
     * Fetching all activities
     * Then:
     * - activities will be returned
     */
    @DisplayName("When fetching all activities then 3 activities will be returned")
    @Test
    void getActivities() {
        try (MockedStatic<ActivityMapper> activityMapper = mockStatic(ActivityMapper.class)) {
            var firstActivity = mock(ActivityEntity.class);
            var secondActivity = mock(ActivityEntity.class);
            var thirdActivity = mock(ActivityEntity.class);
            activityMapper.when(() -> ActivityMapper.fromActivityEntity(firstActivity)).thenReturn(mock(ActivityDetail.class));
            activityMapper.when(() -> ActivityMapper.fromActivityEntity(secondActivity)).thenReturn(mock(ActivityDetail.class));
            activityMapper.when(() -> ActivityMapper.fromActivityEntity(thirdActivity)).thenReturn(mock(ActivityDetail.class));
            when(activityRepository.findAllByCreatedByAndQuestionId("username", QUESTION_ID)).thenReturn(List.of(firstActivity, secondActivity, thirdActivity));

            var activities = ut.getActivities(new GetActivities(QUESTION_ID, "username"));

            assertThat(activities).hasSize(3);
            verify(activityRepository).findAllByCreatedByAndQuestionId("username", QUESTION_ID);
        }
    }
}