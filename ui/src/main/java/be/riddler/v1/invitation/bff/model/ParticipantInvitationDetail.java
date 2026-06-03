package be.riddler.v1.invitation.bff.model;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.invitation.client.model.Invitation;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * ParticipantInvitationDetail
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
public record ParticipantInvitationDetail(@NonNull Invitation invitation, @NonNull List<@NonNull Answer> answers) {
}
