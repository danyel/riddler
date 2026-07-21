package be.riddler.v1.publication.feature;

import be.riddler.v1.publication.client.model.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * FindLevelsByLevelFeature
 *
 * @author dnoulet
 * @version 1.0.0 12/07/2026
 */
public interface FindLevelsByLevelFeature {
    @NonNull List<@NonNull Level> findLevelsByLevel(@NonNull String level);
}
