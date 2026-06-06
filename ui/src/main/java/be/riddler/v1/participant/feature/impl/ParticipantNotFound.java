package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.common.http.NotFound;

import java.util.UUID;

/**
 * ParticipantNotFound
 *
 * @author dnoulet
 * @version 1.0.0 06/06/2026
 */
public class ParticipantNotFound extends NotFound {
    public ParticipantNotFound(UUID participantId) {
        super("Participant", participantId);
    }
}
