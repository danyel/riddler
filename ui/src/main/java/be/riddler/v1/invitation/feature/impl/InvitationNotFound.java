package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.common.http.NotFound;

import java.util.UUID;

/**
 * InvitationNotFound
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
public class InvitationNotFound extends NotFound {
    public InvitationNotFound(UUID invitationId) {
        super("Invitation", invitationId);
    }
}
