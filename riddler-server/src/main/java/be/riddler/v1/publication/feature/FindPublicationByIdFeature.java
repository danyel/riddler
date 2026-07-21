package be.riddler.v1.publication.feature;

import be.riddler.v1.publication.client.model.Publication;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * FindPublicationByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public interface FindPublicationByIdFeature {
    @NonNull Publication findById(@NonNull UUID publicationId);
}
