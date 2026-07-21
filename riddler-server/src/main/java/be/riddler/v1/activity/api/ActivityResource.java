package be.riddler.v1.activity.api;

import be.riddler.common.path.Paths;
import be.riddler.v1.activity.client.ActivityClient;
import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.CreateActivity;
import be.riddler.v1.activity.client.model.GetActivities;
import be.riddler.v1.activity.feature.CreateActivityFeature;
import be.riddler.v1.activity.feature.GetActivitiesFeature;
import be.riddler.v1.settings.constants.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * ActivityResource
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@RestController
@RequiredArgsConstructor(access = PACKAGE)
@HttpExchange(url = Paths.ACTIVITY)
public class ActivityResource implements ActivityClient {
    private final CreateActivityFeature createActivityFeature;
    private final GetActivitiesFeature getActivitiesFeature;

    @Override
    public List<ActivityDetail> getActivities(UUID activityId) {
        return getActivitiesFeature.getActivities(new GetActivities(activityId, Objects.requireNonNull(UserContext.username())));
    }

    @Override
    public ActivityDetail create(CreateActivity createActivity) {
        return createActivityFeature.create(createActivity);
    }
}

