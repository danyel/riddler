package be.riddler.v1.invitation.feature;

import be.riddler.v1.invitation.client.model.Invitation;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * FindByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface FindByIdFeature {
    @NonNull Invitation findById(@NonNull UUID invitationId);
}
