package be.riddler.v1.invitation.feature;

import be.riddler.v1.invitation.client.model.Invitation;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * FindAllByParticipantIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface FindAllByParticipantIdFeature {
    @NonNull List<@NonNull Invitation> findAllByParticipantId(@NonNull UUID participantId);
}
