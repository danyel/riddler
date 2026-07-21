package be.riddler.v1.participant.repository;

import be.riddler.common.repository.AbstractRepositoryIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ParticipantRepositoryIntegrationTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Participant Repository Integration Test")
class ParticipantRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private ParticipantRepository participantRepository;

    @DisplayName("Fetching participant by id")
    @Test
    void findById() {
        var participant = participantRepository.findById(PARTICIPANT_ID);

        assertTrue(participant.isPresent());
        var participantEntity = participant.get();
        assertEquals(PARTICIPANT_ID, participantEntity.getId());
        assertEquals("Daniel", participantEntity.getFirstName());
        assertEquals("Noulet", participantEntity.getLastName());
        assertEquals("daniel.noulet@email.net", participantEntity.getEmail());
    }
}