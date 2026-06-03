package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.client.model.UpdateInvitation;
import be.riddler.v1.invitation.entity.InvitationQuestionEntity;
import be.riddler.v1.invitation.entity.InvitationQuestionId;
import be.riddler.v1.invitation.feature.UpdateInvitationFeature;
import be.riddler.v1.invitation.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

import static be.riddler.v1.invitation.mapper.InvitationMapper.fromInvitationEntity;
import static lombok.AccessLevel.PACKAGE;

/**
 * UpdateInvitationFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 24/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class UpdateInvitationFeatureImpl implements UpdateInvitationFeature {
    private final InvitationRepository invitationRepository;

    @Transactional
    @Override
    public Invitation updateInvitation(UUID invitationId, UpdateInvitation updateInvitation) {
        var invitationEntity = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationNotFound(invitationId));
        var savedQuestions = invitationEntity.getQuestions()
                .stream()
                .collect(Collectors.toMap(
                        invitationQuestionEntity -> invitationQuestionEntity.getId().questionId(),
                        (invitationQuestionEntity) -> invitationQuestionEntity)
                );
        var questionsToBeRemoved = invitationEntity.getQuestions()
                .stream()
                .filter(question -> !updateInvitation.questions().contains(question.getId().questionId()))
                .toList();
        invitationEntity.getQuestions().removeAll(questionsToBeRemoved);
        invitationEntity.getQuestions().addAll(updateInvitation.questions()
                .stream()
                .filter(uuid -> !savedQuestions.containsKey(uuid))
                .map(uuid -> InvitationQuestionEntity.builder()
                        .id(new InvitationQuestionId(invitationId, uuid))
                        .invitation(invitationEntity)
                        .build())
                .toList());
        invitationRepository.save(invitationEntity);
        return fromInvitationEntity(invitationEntity);
    }
}
