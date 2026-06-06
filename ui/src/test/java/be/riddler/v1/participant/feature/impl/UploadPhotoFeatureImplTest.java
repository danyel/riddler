package be.riddler.v1.participant.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.participant.entity.ParticipantEntity;
import be.riddler.v1.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT_ID;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * UploadPhotoFeatureImplTest
 *
 * @author dnoulet
 * @version 1.0.0 06/06/2026
 */
@DisplayName("Upload Photo Feature Test")
class UploadPhotoFeatureImplTest extends AbstractMockTest {
    @Mock
    private ParticipantRepository participantRepository;
    @InjectMocks
    private UploadPhotoFeatureImpl ut;
    private final byte[] data = "data".getBytes();

    @DisplayName("Given a valid participant id when uploading the photo then the photo is stored")
    @Test
    void uploadPhoto() {
        var participantEntity = mock(ParticipantEntity.class);
        when(participantRepository.findById(PARTICIPANT_ID)).thenReturn(of(participantEntity));

        ut.uploadPhoto(PARTICIPANT_ID, data);

        var inOrder = inOrder(participantRepository, participantEntity);
        inOrder.verify(participantRepository).findById(PARTICIPANT_ID);
        inOrder.verify(participantEntity).setPhoto(data);
        inOrder.verify(participantRepository).save(participantEntity);
        inOrder.verifyNoMoreInteractions();
    }

    @DisplayName("Given an invalid participant id when uploading photo then participant not found will be thrown")
    @Test
    void uploadPhotThrowsParticipantNotFound() {
        when(participantRepository.findById(PARTICIPANT_ID)).thenReturn(empty());

        var participantNotFound = assertThrows(ParticipantNotFound.class, () -> ut.uploadPhoto(PARTICIPANT_ID, data));
        assertEquals("Participant with id '" + PARTICIPANT_ID + "' not found", participantNotFound.getMessage());
    }
}