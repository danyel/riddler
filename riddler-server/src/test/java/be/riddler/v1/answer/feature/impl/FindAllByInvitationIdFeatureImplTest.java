package be.riddler.v1.answer.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.invitation.repository.InvitationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static be.riddler.v1.fixture.Fixture.Answer.ANSWER_ENTITY;
import static be.riddler.v1.fixture.Fixture.Invitation.INVITATION_ENTITY;
import static be.riddler.v1.fixture.Fixture.Invitation.INVITATION_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

/**
 * FindAllByInvitationIdFeatureImplTest
 *
 * @author dnoulet
 * @version 1.0.0 22/07/2026
 */
@DisplayName("Find All By Invitation Id Feature Test")
class FindAllByInvitationIdFeatureImplTest extends AbstractMockTest {
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private InvitationRepository invitationRepository;
    @InjectMocks
    private FindAllByInvitationIdFeatureImpl ut;

    @DisplayName("Given a valid invitation id when retrieving the answers then a list will be returned")
    @Test
    void findAllByInvitationId() {
        try (var answerMapper = mockStatic(AnswerMapper.class)) {
            given(invitationRepository.findById(INVITATION_ID)).willReturn(Optional.of(INVITATION_ENTITY));
            given(answerRepository.findAllByInvitationId(INVITATION_ID)).willReturn(List.of(ANSWER_ENTITY));
            var answer = mock(Answer.class);
            answerMapper.when(() -> AnswerMapper.fromAnswerEntity(ANSWER_ENTITY)).thenReturn(answer);

            var answers = ut.findAllByInvitationId(INVITATION_ID);

            assertThat(answers).hasSize(1);
            assertThat(answers.getFirst()).isSameAs(answer);
        }
    }
}