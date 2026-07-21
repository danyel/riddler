package be.riddler.v1.participant.feature;

import be.riddler.v1.participant.client.model.Participant;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * FindAllParticipantsFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface FindAllParticipantsFeature {
    @NonNull List<@NonNull Participant> findAll();
}
