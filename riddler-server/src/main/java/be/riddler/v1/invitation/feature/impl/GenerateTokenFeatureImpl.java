package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.feature.GenerateTokenFeature;
import be.riddler.v1.invitation.mapper.InvitationMapper;
import be.riddler.v1.invitation.repository.InvitationRepository;
import be.riddler.v1.participant.codec.TokenProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * GenerateTokenFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class GenerateTokenFeatureImpl implements GenerateTokenFeature {
    private final InvitationRepository invitationRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public @NonNull Invitation generate(@NonNull UUID invitationId) {
        var invitationEntity = invitationRepository.findById(invitationId);
        if (invitationEntity.isPresent()) {
            var invitation = invitationEntity.get();
            var generateToken = tokenProvider.generateToken();
            invitation.setStoredToken(generateToken);
            invitationRepository.save(invitation);
            return InvitationMapper.fromInvitationEntity(invitation);
        }
        throw new EntityNotFoundException("Invitation not found with id: %s".formatted(invitationId));
    }
}
