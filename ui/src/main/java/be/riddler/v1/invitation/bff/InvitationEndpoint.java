package be.riddler.v1.invitation.bff;

import be.riddler.v1.common.security.Roles;
import be.riddler.v1.invitation.client.InvitationClient;
import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.client.model.UpdateInvitation;
import be.riddler.v1.question.client.QuestionClient;
import be.riddler.v1.question.client.model.Question;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * InvitationEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@BrowserCallable
@RolesAllowed({Roles.ADMIN, Roles.USER})
@RequiredArgsConstructor(access = PACKAGE)
public class InvitationEndpoint {
    private final InvitationClient invitationClient;
    private final QuestionClient questionClient;

    public @NonNull Invitation create(@NonNull CreateInvitation createInvitation) {
        return invitationClient.create(createInvitation);
    }

    public @NonNull Invitation findById(@NonNull UUID invitationId) {
        return invitationClient.findById(invitationId);
    }

    public @NonNull Invitation findByToken(@NonNull String token) {
        return invitationClient.findByToken(token);
    }

    public void delete(@NonNull UUID invitationId) {
        invitationClient.deleteById(invitationId);
    }

    public @NonNull List<@NonNull Invitation> findInvitationsByParticipantId(@NonNull UUID participantId) {
        return invitationClient.findInvitationsByParticipantId(participantId);
    }

    public @NonNull List<@NonNull Question> findByIds(@NonNull List<@NonNull UUID> questionIds) {
        return questionClient.getQuestionsById(questionIds);
    }

    public @NonNull Invitation generateToken(UUID participantId) {
        return invitationClient.generateToken(participantId);
    }

    public @NonNull Invitation addQuestions(@NonNull UUID invitationId, @NonNull List<@NonNull UUID> questionIds) {
        return invitationClient.update(invitationId, new UpdateInvitation(questionIds));
    }
}
