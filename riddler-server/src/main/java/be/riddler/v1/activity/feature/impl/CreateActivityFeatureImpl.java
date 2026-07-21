package be.riddler.v1.activity.feature.impl;

import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.CreateActivity;
import be.riddler.v1.activity.feature.CreateActivityFeature;
import be.riddler.v1.activity.mapper.ActivityMapper;
import be.riddler.v1.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreateActivityFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class CreateActivityFeatureImpl implements CreateActivityFeature {
    private final ActivityRepository activityRepository;

    @Transactional
    @Override
    public @NonNull ActivityDetail create(@NonNull CreateActivity createActivity) {
        var activityEntity = ActivityMapper.fromCreateActivity(createActivity);
        activityRepository.save(activityEntity);
        return ActivityMapper.fromActivityEntity(activityEntity);
    }
}
