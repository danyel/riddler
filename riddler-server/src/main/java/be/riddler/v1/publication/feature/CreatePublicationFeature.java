package be.riddler.v1.publication.feature;

import be.riddler.v1.publication.client.model.CreatePublication;
import be.riddler.v1.publication.client.model.Publication;
import org.jspecify.annotations.NonNull;

/**
 * CreatePublicationFeature
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public interface CreatePublicationFeature {
    @NonNull Publication create(@NonNull CreatePublication createPublication);
}
