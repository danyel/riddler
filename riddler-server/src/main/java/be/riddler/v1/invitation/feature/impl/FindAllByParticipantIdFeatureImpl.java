package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.entity.InvitationEntity;
import be.riddler.v1.invitation.feature.FindAllByParticipantIdFeature;
import be.riddler.v1.invitation.mapper.InvitationMapper;
import be.riddler.v1.invitation.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
class FindAllByParticipantIdFeatureImpl implements FindAllByParticipantIdFeature {
    private final InvitationRepository invitationRepository;

    @Transactional
    @Override
    public @NonNull List<@NonNull Invitation> findAllByParticipantId(@NonNull UUID participantId) {
        var invitations = invitationRepository.findAllByParticipantId(participantId);

        return Objects.requireNonNullElse(invitations, new ArrayList<InvitationEntity>())
                .stream()
                .map(InvitationMapper::fromInvitationEntity)
                .toList();
    }
}
