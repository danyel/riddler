package be.riddler.v1.activity.repository;

import be.riddler.common.repository.AbstractRepositoryIntegrationTest;
import be.riddler.v1.activity.entity.ActivityEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;

import static be.riddler.v1.fixture.Fixture.Question.DB_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * ActivityRepositoryTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Activity Repository")
class ActivityRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        activityRepository.save(
                ActivityEntity.builder()
                        .questionId(DB_ID)
                        .elementId("elementId")
                        .additionalData("additionalData")
                        .timestamp(Instant.now())
                        .actionType("actionType")
                        .createdBy("user")
                        .updatedBy("user")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
    }

    @Test
    void findAllByCreatedByAndQuestionId() {
        var activities = activityRepository.findAllByCreatedByAndQuestionId("user", DB_ID);
        assertNotNull(activities);
        assertFalse(activities.isEmpty());
        assertEquals(1, activities.size());
    }
}