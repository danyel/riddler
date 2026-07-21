package be.riddler.v1.publication.feature;

import be.riddler.v1.publication.client.model.CreateLevel;
import be.riddler.v1.publication.client.model.Level;
import org.jspecify.annotations.NonNull;

/**
 * CreateLevelFeature
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public interface CreateLevelFeature {
    @NonNull Level create(@NonNull CreateLevel createLevel);
}
