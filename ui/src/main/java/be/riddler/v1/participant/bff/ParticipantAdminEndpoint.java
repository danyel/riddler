package be.riddler.v1.participant.bff;

import be.riddler.v1.participant.client.ParticipantClient;
import be.riddler.v1.participant.client.model.CreateParticipant;
import be.riddler.v1.participant.client.model.Participant;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * ParticipantEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@BrowserCallable
@RolesAllowed("ADMIN")
@RequiredArgsConstructor(access = PACKAGE)
public class ParticipantAdminEndpoint {
    private final ParticipantClient participantClient;

    public @NonNull List<@NonNull Participant> findAll() {
        return participantClient.findAll();
    }

    public @NonNull Participant findById(@NonNull UUID id) {
        return participantClient.findById(id);
    }

    public @NonNull Participant create(CreateParticipant createParticipant) {
        return participantClient.create(createParticipant);
    }
}
