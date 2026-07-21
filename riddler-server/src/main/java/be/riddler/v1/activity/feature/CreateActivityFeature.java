package be.riddler.v1.activity.feature;

import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.CreateActivity;
import org.jspecify.annotations.NonNull;

/**
 * CreateActivityFeature
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
public interface CreateActivityFeature {
    @NonNull ActivityDetail create(@NonNull CreateActivity createActivity);
}
