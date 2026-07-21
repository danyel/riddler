package be.riddler.v1.participant.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.participant.client.model.ParticipantId;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT;
import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT_ENTITY;
import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT_ID;
import static be.riddler.v1.participant.mapper.ParticipantMapper.fromParticipantEntity;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

/**
 * FindByParticipantIdFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Find By Participant Id Feature Test")
class FindByParticipantIdFeatureTest extends AbstractMockTest {
    @Mock
    private ParticipantRepository participantRepository;
    @InjectMocks
    private FindByParticipantIdFeatureImpl ut;

    @DisplayName("Given valid participant id when searching for it then the participant will be returned")
    @Test
    void findById() {
        try (var participantMapper = mockStatic(ParticipantMapper.class)) {
            given(participantRepository.findById(PARTICIPANT_ID)).willReturn(of(PARTICIPANT_ENTITY));
            participantMapper.when(() -> fromParticipantEntity(PARTICIPANT_ENTITY)).thenReturn(PARTICIPANT);
            var participant = ut.findByParticipantId(new ParticipantId(PARTICIPANT_ID));
            assertEquals(PARTICIPANT_ID, participant.id());
            assertEquals(PARTICIPANT.firstName(), participant.firstName());
            assertEquals(PARTICIPANT.lastName(), participant.lastName());
            assertEquals(PARTICIPANT.emailAddress(), participant.emailAddress());
            assertEquals(PARTICIPANT.cv(), participant.cv());
            assertEquals(PARTICIPANT.photo(), participant.photo());
            assertEquals(PARTICIPANT.categories(), participant.categories());
        }
    }

    @DisplayName("Given invalid participant id when searching for it then participant not found will be thrown")
    @Test
    void throwsParticipantNotFound() {
        given(participantRepository.findById(PARTICIPANT_ID)).willReturn(empty());
        var participantNotFound = assertThrows(ParticipantNotFound.class, () -> ut.findByParticipantId(new ParticipantId(PARTICIPANT_ID)));
        assertEquals("Participant with id '" + PARTICIPANT_ID + "' not found", participantNotFound.getMessage());
    }
}
