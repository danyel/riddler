package be.riddler.v1.answer.feature.impl;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.feature.FindAllByInvitationIdFeature;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.invitation.feature.impl.InvitationNotFound;
import be.riddler.v1.invitation.repository.InvitationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * FindAllByInvitationIdFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 12/07/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FindAllByInvitationIdFeatureImpl implements FindAllByInvitationIdFeature {
    private final AnswerRepository answerRepository;
    private final InvitationRepository invitationRepository;

    @Override
    public List<Answer> findAllByInvitationId(UUID invitationId) {
        invitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationNotFound(invitationId));
        return answerRepository.findAllByInvitationId(invitationId)
                .stream()
                .map(AnswerMapper::fromAnswerEntity)
                .toList();
    }
}
