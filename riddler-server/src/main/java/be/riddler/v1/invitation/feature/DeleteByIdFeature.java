package be.riddler.v1.invitation.feature;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * DeleteByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface DeleteByIdFeature {
    void deleteById(@NonNull UUID invitationId);
}
