package be.riddler.v1.activity.mapper;

import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.CreateActivity;
import be.riddler.v1.activity.entity.ActivityEntity;
import lombok.experimental.UtilityClass;

/**
 * ActivityMapper
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@UtilityClass
public class ActivityMapper {
    public ActivityEntity fromCreateActivity(CreateActivity createActivity) {
        return ActivityEntity.builder()
                .actionType(createActivity.actionType())
                .questionId(createActivity.questionId())
                .timestamp(createActivity.timestamp())
                .elementId(createActivity.elementId())
                .additionalData(createActivity.additionalData())
                .build();
    }

    public ActivityDetail fromActivityEntity(ActivityEntity activityEntity) {
        return new ActivityDetail(activityEntity.getId(), activityEntity.getQuestionId(), activityEntity.getActionType(), activityEntity.getElementId(), activityEntity.getTimestamp(), activityEntity.getAdditionalData());
    }
}
