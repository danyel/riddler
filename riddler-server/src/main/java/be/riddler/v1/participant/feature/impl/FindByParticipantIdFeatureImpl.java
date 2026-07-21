package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.participant.client.model.Participant;
import be.riddler.v1.participant.client.model.ParticipantId;
import be.riddler.v1.participant.feature.FindByParticipantIdFeature;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindParticipantByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindByParticipantIdFeatureImpl implements FindByParticipantIdFeature {
    private final ParticipantRepository participantRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull Participant findByParticipantId(@NonNull ParticipantId participantId) {
        return participantRepository.findById(participantId.id())
                .map(ParticipantMapper::fromParticipantEntity)
                .orElseThrow(() -> new ParticipantNotFound(participantId.id()));
    }
}
