package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.feature.FindByTokenFeature;
import be.riddler.v1.invitation.mapper.InvitationMapper;
import be.riddler.v1.invitation.repository.InvitationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

/**
 * FindByTokenFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 12/07/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class FindByTokenFeatureImpl implements FindByTokenFeature {
    private final InvitationRepository invitationRepository;

    @Override
    public @NonNull Invitation findByToken(@NonNull String token) {
        var invitationEntity = invitationRepository.findByStoredToken(token);
        return invitationEntity.map(InvitationMapper::fromInvitationEntity).orElseThrow();
    }
}
