package be.riddler.v1.invitation.entity;

import be.riddler.v1.common.entity.BaseEntity;
import be.riddler.v1.publication.entity.PublicationEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * InvitationEntity
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "invitations")
public class InvitationEntity extends BaseEntity {
    private UUID participantId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id", nullable = false)
    private PublicationEntity publication;
    @OneToMany(mappedBy = "invitation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<InvitationQuestionEntity> questions = new ArrayList<>();
    private String storedToken;
}
