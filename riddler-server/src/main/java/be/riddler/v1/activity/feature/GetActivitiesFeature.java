package be.riddler.v1.activity.feature;

import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.GetActivities;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * GetActivitiesFeature
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
public interface GetActivitiesFeature {
    @NonNull List<@NonNull ActivityDetail> getActivities(@NonNull GetActivities getActivities);
}
