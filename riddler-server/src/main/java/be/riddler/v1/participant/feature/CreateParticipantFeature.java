package be.riddler.v1.participant.feature;

import be.riddler.v1.participant.client.model.CreateParticipant;
import be.riddler.v1.participant.client.model.Participant;
import org.jspecify.annotations.NonNull;

/**
 * CreateParticipantFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface CreateParticipantFeature {
    @NonNull Participant create(@NonNull CreateParticipant createParticipant);
}
