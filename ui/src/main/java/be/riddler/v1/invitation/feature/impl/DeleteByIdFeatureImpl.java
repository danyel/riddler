package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.feature.DeleteByIdFeature;
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
class DeleteByIdFeatureImpl implements DeleteByIdFeature {
    private final InvitationRepository invitationRepository;

    @Transactional
    @Override
    public void deleteById(@NonNull UUID invitationId) {
        invitationRepository.deleteById(invitationId);
    }
}
