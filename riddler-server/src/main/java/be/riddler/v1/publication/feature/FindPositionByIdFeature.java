package be.riddler.v1.publication.feature;

import be.riddler.v1.publication.client.model.Position;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * FindPositionByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public interface FindPositionByIdFeature {
    @NonNull Position findById(UUID positionId);
}
