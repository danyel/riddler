package be.riddler.v1.participant.bff;

import be.riddler.v1.invitation.client.InvitationClient;
import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.participant.client.ParticipantClient;
import be.riddler.v1.participant.client.model.Participant;
import be.riddler.v1.question.client.QuestionClient;
import be.riddler.v1.question.client.model.Question;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
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
@AnonymousAllowed
@RequiredArgsConstructor(access = PACKAGE)
public class ParticipantEndpoint {
    private final QuestionClient questionClient;
    private final ParticipantClient participantClient;
    private final InvitationClient invitationClient;

    public @NonNull List<@NonNull Question> getQuestions() {
        return questionClient.getQuestionsById(List.of());
    }

    public @NonNull Question get(@NonNull UUID uuid) {
        return questionClient.findById(uuid);
    }

    public @NonNull Participant findById(@NonNull UUID uuid) {
        return participantClient.findById(uuid);
    }

    public @NonNull Invitation findByToken(@NonNull String token) {
        return invitationClient.findByToken(token);
    }
}
