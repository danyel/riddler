package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.client.model.UpdateInvitation;
import be.riddler.v1.invitation.repository.InvitationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static be.riddler.v1.fixture.Fixture.Invitation.INVITATION_ID;
import static be.riddler.v1.fixture.Fixture.Invitation.createInvitationEntityWithQuestions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * UpdateInvitationFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Update Invitation Feature")
@ExtendWith(MockitoExtension.class)
class UpdateInvitationFeatureTest {
    @Mock
    private InvitationRepository invitationRepository;
    @InjectMocks
    private UpdateInvitationFeatureImpl ut;

    @Test
    void updateInvitation() {
        var invitationEntity = createInvitationEntityWithQuestions();
        given(invitationRepository.findById(INVITATION_ID)).willReturn(Optional.of(invitationEntity));
        var randomUUID = UUID.randomUUID();

        var invitation = ut.updateInvitation(INVITATION_ID, new UpdateInvitation(List.of(randomUUID)));

        then(invitationRepository).should().findById(INVITATION_ID);
        assertThat(invitation.questions()).hasSize(1);
        assertThat(invitation.questions().getFirst()).isEqualTo(randomUUID);
    }

    @DisplayName("Given invalid invitation id when retrieving the invitation then an invitation not found is thrown")
    @Test
    void updateInvitationThrowsInvitationNotFound() {
        given(invitationRepository.findById(INVITATION_ID)).willReturn(Optional.empty());

        var invitationNotFound = Assertions.assertThrows(InvitationNotFound.class, () -> ut.updateInvitation(INVITATION_ID, any(UpdateInvitation.class)));
        assertEquals("Invitation with id '%s' not found".formatted(INVITATION_ID), invitationNotFound.getDescription());
        then(invitationRepository).should().findById(INVITATION_ID);
        then(invitationRepository).shouldHaveNoMoreInteractions();
    }
}