package be.riddler.v1.invitation.mapper;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.invitation.client.model.CreateInvitation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static be.riddler.v1.fixture.Fixture.Invitation.INVITATION_ENTITY;
import static be.riddler.v1.fixture.Fixture.Invitation.INVITATION_ENTITY_WITH_QUESTIONS;
import static be.riddler.v1.fixture.Fixture.Invitation.INVITATION_ID;
import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT_ID;
import static be.riddler.v1.fixture.Fixture.Publication.PUBLICATION_ID;
import static be.riddler.v1.fixture.Fixture.Question.QUESTION_ID;
import static be.riddler.v1.invitation.mapper.InvitationMapper.fromCreateInvitation;
import static be.riddler.v1.invitation.mapper.InvitationMapper.fromInvitationEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * InvitationMapperTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Invitation Mapper")
class InvitationMapperTest extends AbstractMockTest {
    @DisplayName("1 Map Create Invitation")
    @Test
    void mapCreateInvitation() {
        var invitationEntity = fromCreateInvitation(new CreateInvitation(PARTICIPANT_ID, PUBLICATION_ID));

        assertEquals(PARTICIPANT_ID, invitationEntity.getParticipantId());
        assertNull(invitationEntity.getPublication());
        assertThat(invitationEntity.getQuestions()).isEmpty();
    }

    @DisplayName("2 Map Invitation Entity")
    @Test
    void mapEntity() {
        var invitation = fromInvitationEntity(INVITATION_ENTITY);

        assertEquals(INVITATION_ID, invitation.id());
        assertEquals(PARTICIPANT_ID, invitation.participantId());
        assertNotNull(invitation.publication());
        assertThat(invitation.questions()).isEmpty();
        assertEquals("storedToken", invitation.storedToken());
    }

    @DisplayName("3 Map Invitation Entity with questions")
    @Test
    void mapEntityWithQuestions() {
        var invitation = fromInvitationEntity(INVITATION_ENTITY_WITH_QUESTIONS);

        assertEquals(INVITATION_ID, invitation.id());
        assertEquals(PARTICIPANT_ID, invitation.participantId());
        assertNotNull(invitation.publication());
        assertEquals("storedToken", invitation.storedToken());
        assertThat(invitation.questions()).hasSize(1);
        assertEquals(QUESTION_ID, invitation.questions().getFirst());
    }
}