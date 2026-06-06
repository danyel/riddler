package be.riddler.v1.participant.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.participant.client.model.CreateParticipant;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT;
import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT_ENTITY;
import static be.riddler.v1.participant.mapper.ParticipantMapper.fromCreateParticipant;
import static be.riddler.v1.participant.mapper.ParticipantMapper.fromParticipantEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * CreateParticipantFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Create Participant Feature Test")
class CreateParticipantFeatureTest extends AbstractMockTest {
    @Mock
    private ParticipantRepository participantRepository;
    @InjectMocks
    private CreateParticipantFeatureImpl ut;

    @DisplayName("Given a valid participant when creating the participant then participant will be returned")
    @Test
    void createParticipant() {
        try (var participantMapper = mockStatic(ParticipantMapper.class)) {
            var createParticipant = new CreateParticipant("First", "Last", "Email");
            participantMapper.when(() -> fromCreateParticipant(createParticipant)).thenReturn(PARTICIPANT_ENTITY);
            participantMapper.when(() -> fromParticipantEntity(PARTICIPANT_ENTITY)).thenReturn(PARTICIPANT);

            var participant = ut.create(createParticipant);

            verify(participantRepository).save(PARTICIPANT_ENTITY);
            verifyNoMoreInteractions(participantRepository);
            assertEquals(PARTICIPANT, participant);
        }
    }
}