package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.participant.feature.UploadPhotoFeature;
import be.riddler.v1.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * UploadPhotoFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class UploadPhotoFeatureImpl implements UploadPhotoFeature {
    private final ParticipantRepository participantRepository;

    @Transactional
    @Override
    public void uploadPhoto(UUID participantId, byte[] data) {
        participantRepository.findById(participantId)
                .ifPresentOrElse(participant -> {
                    participant.setPhoto(data);
                    participantRepository.save(participant);
                }, () -> {
                    throw new ParticipantNotFound(participantId);
                });
    }
}
