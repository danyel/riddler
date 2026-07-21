package be.riddler.v1.invitation.feature;

import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.Invitation;
import org.jspecify.annotations.NonNull;

/**
 * SaveInvitationFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface SaveInvitationFeature {
    Invitation save(@NonNull CreateInvitation createInvitation);
}
