package be.riddler.v1.publication.feature;

import be.riddler.v1.publication.client.model.Position;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * FindAllPositionsFeature
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public interface FindAllPositionsFeature {
    @NonNull List<Position> findAllPositions();
}
