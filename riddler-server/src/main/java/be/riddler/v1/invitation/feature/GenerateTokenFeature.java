package be.riddler.v1.invitation.feature;

import be.riddler.v1.invitation.client.model.Invitation;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * GenerateTokenFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface GenerateTokenFeature {
  @NonNull Invitation generate(@NonNull UUID invitationId);
}
