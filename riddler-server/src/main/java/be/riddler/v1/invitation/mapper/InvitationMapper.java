package be.riddler.v1.invitation.mapper;

import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.entity.InvitationEntity;
import be.riddler.v1.publication.mapper.PublicationMapper;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.UUID;

/**
 * InvitationMapper
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@UtilityClass
public class InvitationMapper {
    public static InvitationEntity fromCreateInvitation(CreateInvitation createInvitation) {
        return InvitationEntity.builder().participantId(createInvitation.participantId()).build();
    }

    public static Invitation fromInvitationEntity(InvitationEntity invitationEntity) {
        List<UUID> safeQuestions = invitationEntity.getQuestions()
                .stream()
                .map(e -> e.getId().questionId())
                .toList();
        var publication = PublicationMapper.fromEntity(invitationEntity.getPublication());
        return safeQuestions.isEmpty()
                ? new Invitation(invitationEntity.getId(), invitationEntity.getParticipantId(), publication, invitationEntity.getStoredToken())
                : new Invitation(invitationEntity.getId(), publication, invitationEntity.getParticipantId(), invitationEntity.getStoredToken(), safeQuestions);
    }
}
