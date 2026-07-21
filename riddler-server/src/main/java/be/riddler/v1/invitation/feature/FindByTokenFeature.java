package be.riddler.v1.invitation.feature;

import be.riddler.v1.invitation.client.model.Invitation;
import org.jspecify.annotations.NonNull;

/**
 * FindByTokenFeature
 *
 * @author dnoulet
 * @version 1.0.0 12/07/2026
 */
public interface FindByTokenFeature {
    @NonNull Invitation findByToken(@NonNull String token);
}
