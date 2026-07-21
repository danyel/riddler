package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.feature.SaveInvitationFeature;
import be.riddler.v1.invitation.mapper.InvitationMapper;
import be.riddler.v1.invitation.repository.InvitationRepository;
import be.riddler.v1.publication.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindInvitationByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class SaveInvitationFeatureImpl implements SaveInvitationFeature {
    private final InvitationRepository invitationRepository;
    private final PublicationRepository publicationRepository;

    @Transactional
    @Override
    public Invitation save(@NonNull CreateInvitation createInvitation) {
        var invitationEntity = InvitationMapper.fromCreateInvitation(createInvitation);
        publicationRepository.findById(createInvitation.publicationId())
                .ifPresent(invitationEntity::setPublication);
        invitationRepository.save(invitationEntity);
        return InvitationMapper.fromInvitationEntity(invitationEntity);
    }
}
