package be.riddler.v1.publication.feature;

import be.riddler.v1.publication.client.model.CreatePosition;
import be.riddler.v1.publication.client.model.Position;
import org.jspecify.annotations.NonNull;

/**
 * CreatePositionFeature
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public interface CreatePositionFeature {
    @NonNull Position create(@NonNull CreatePosition createPosition);
}
