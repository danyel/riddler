package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.participant.client.model.CreateParticipant;
import be.riddler.v1.participant.client.model.Participant;
import be.riddler.v1.participant.feature.CreateParticipantFeature;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreateParticipantFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class CreateParticipantFeatureImpl implements CreateParticipantFeature {
    private final ParticipantRepository participantRepository;

    @Transactional
    public @NonNull Participant create(@NonNull CreateParticipant createParticipant) {
        var participantEntity = ParticipantMapper.fromCreateParticipant(createParticipant);
        participantRepository.save(participantEntity);
        return ParticipantMapper.fromParticipantEntity(participantEntity);
    }
}
