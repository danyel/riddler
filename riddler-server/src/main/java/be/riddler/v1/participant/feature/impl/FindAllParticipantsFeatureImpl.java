package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.participant.client.model.Participant;
import be.riddler.v1.participant.feature.FindAllParticipantsFeature;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindParticipantByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindAllParticipantsFeatureImpl implements FindAllParticipantsFeature {
    private final ParticipantRepository participantRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull List<@NonNull Participant> findAll() {
        return participantRepository.findAll()
                .stream()
                .map(ParticipantMapper::fromParticipantEntity)
                .toList();
    }
}
