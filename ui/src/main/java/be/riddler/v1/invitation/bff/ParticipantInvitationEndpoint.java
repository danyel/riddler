package be.riddler.v1.invitation.bff;

import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.invitation.bff.model.ParticipantInvitationDetail;
import be.riddler.v1.invitation.client.InvitationClient;
import be.riddler.v1.question.client.QuestionClient;
import be.riddler.v1.question.client.model.Question;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * ParticipantInvitationEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 29/05/2026
 */
@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor(access = PACKAGE)
public class ParticipantInvitationEndpoint {
    private final InvitationClient invitationClient;
    private final QuestionClient questionClient;
    private final AnswerRepository answerRepository;

    public @NonNull List<@NonNull Question> findByIds(@NonNull List<@NonNull UUID> questionIds) {
        return questionClient.getQuestionsById(questionIds);
    }

    public @NonNull ParticipantInvitationDetail findById(@NonNull UUID invitationId) {
        var invitation = invitationClient.findById(invitationId);
        var answers = answerRepository.findAllByInvitationId(invitationId);
        return new ParticipantInvitationDetail(
                invitation,
                answers.stream()
                        .map(AnswerMapper::fromAnswerEntity)
                        .toList()
        );
    }
}
