package be.riddler.v1.invitation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

/**
 * InvitationQuestionId
 *
 * @author dnoulet
 * @version 1.0.0 24/05/2026
 */
@Embeddable
public record InvitationQuestionId(
        @Column UUID invitationId,
        @Column UUID questionId
) implements Serializable {
}
