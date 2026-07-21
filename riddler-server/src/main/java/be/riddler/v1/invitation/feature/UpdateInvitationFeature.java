package be.riddler.v1.invitation.feature;

import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.client.model.UpdateInvitation;

import java.util.UUID;

/**
 * UpdateInvitationFeature
 *
 * @author dnoulet
 * @version 1.0.0 24/05/2026
 */
public interface UpdateInvitationFeature {
    Invitation updateInvitation(UUID invitationId, UpdateInvitation updateInvitation);
}
