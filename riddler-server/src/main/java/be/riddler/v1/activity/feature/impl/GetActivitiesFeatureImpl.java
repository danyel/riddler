package be.riddler.v1.activity.feature.impl;

import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.GetActivities;
import be.riddler.v1.activity.feature.GetActivitiesFeature;
import be.riddler.v1.activity.mapper.ActivityMapper;
import be.riddler.v1.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * GetActivitiesFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
public class GetActivitiesFeatureImpl implements GetActivitiesFeature {
    private final ActivityRepository activityRepository;

    @Transactional
    @Override
    public @NonNull List<@NonNull ActivityDetail> getActivities(@NonNull GetActivities getActivities) {
        return activityRepository.findAllByCreatedByAndQuestionId(getActivities.username(), getActivities.questionId())
                .stream()
                .map(ActivityMapper::fromActivityEntity)
                .toList();
    }
}
