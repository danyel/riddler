package be.riddler.v1.invitation.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * InvitationQuestionEntity
 *
 * @author dnoulet
 * @version 1.0.0 24/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "invitation_questions")
public class InvitationQuestionEntity {
    @EmbeddedId
    private InvitationQuestionId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("invitationId")
    // Fixed: Maps the 'invitationId' attribute directly inside your InvitationQuestionId record/class
    @JoinColumn(name = "invitation_id", nullable = false)
    private InvitationEntity invitation;
}
