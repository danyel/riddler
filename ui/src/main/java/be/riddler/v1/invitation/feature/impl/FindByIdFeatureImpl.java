package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.feature.FindByIdFeature;
import be.riddler.v1.invitation.mapper.InvitationMapper;
import be.riddler.v1.invitation.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindInvitationByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindByIdFeatureImpl implements FindByIdFeature {
    private final InvitationRepository invitationRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull Invitation findById(@NonNull UUID invitationId) {
        var invitation = invitationRepository.findById(invitationId);

        if (invitation.isPresent()) {
            return InvitationMapper.fromInvitationEntity(invitation.get());
        }

        throw new RuntimeException("Invitation with id " + invitationId + " not found");
    }
}
