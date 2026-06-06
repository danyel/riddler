package be.riddler.v1.participant.bff;

import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.mapper.InvitationMapper;
import be.riddler.v1.invitation.repository.InvitationRepository;
import be.riddler.v1.participant.client.ParticipantClient;
import be.riddler.v1.participant.client.model.Participant;
import be.riddler.v1.question.client.QuestionClient;
import be.riddler.v1.question.client.model.Question;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

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
    private final QuestionClient questionApi;
    private final ParticipantClient participantClient;
    private final InvitationRepository invitationRepository;

    public @NonNull List<@NonNull Question> getQuestions() {
        return questionApi.getQuestionsById(List.of());
    }

    public @NonNull Question get(@NonNull UUID uuid) {
        return questionApi.findById(uuid);
    }

    public @NonNull Participant findById(@NonNull UUID uuid) {
        return participantClient.findById(uuid);
    }

    @Transactional(readOnly = true)
    public @NonNull Invitation findByToken(@NonNull String token) {
        var invitationEntity = invitationRepository.findByStoredToken(token);
        return invitationEntity.map(InvitationMapper::fromInvitationEntity).orElseThrow();
    }
}
