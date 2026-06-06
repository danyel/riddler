package be.riddler.v1.participant.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT;
import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT_ENTITY;
import static be.riddler.v1.participant.mapper.ParticipantMapper.fromParticipantEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

/**
 * FindAllParticipantsFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Find All Participants Feature Test")
class FindAllParticipantsFeatureTest extends AbstractMockTest {
    @Mock
    private ParticipantRepository participantRepository;
    @InjectMocks
    private FindAllParticipantsFeatureImpl ut;

    @DisplayName("When fetching all then a list of participants will be returned")
    @Test
    void findAll() {
        try (var participantMapper = mockStatic(ParticipantMapper.class)) {
            given(participantRepository.findAll()).willReturn(List.of(PARTICIPANT_ENTITY));
            participantMapper.when(() -> fromParticipantEntity(PARTICIPANT_ENTITY)).thenReturn(PARTICIPANT);

            var participants = ut.findAll();
            assertEquals(1, participants.size());
        }
    }
}