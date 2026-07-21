package be.riddler.v1.publication.feature;

import be.riddler.v1.publication.client.model.Position;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * FindPositionsByPositionFeature
 *
 * @author dnoulet
 * @version 1.0.0 12/07/2026
 */
public interface FindPositionsByPositionFeature {
    @NonNull List<@NonNull Position> findPositionsByPosition(@NonNull String position);
}
