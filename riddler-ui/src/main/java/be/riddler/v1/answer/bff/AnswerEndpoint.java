package be.riddler.v1.answer.bff;

import be.riddler.v1.answer.client.AnswerClient;
import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.client.model.UpdateAnswer;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * AnswerEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@PermitAll
@BrowserCallable
@RequiredArgsConstructor(access = PACKAGE)
public class AnswerEndpoint {
    private final AnswerClient answerClient;

    public @NonNull List<@NonNull Answer> findByQuestion(UUID questionId) {
        return answerClient.findByQuestionId(questionId);
    }

    public @NonNull Answer create(@NonNull CreateAnswer createAnswer) {
        return answerClient.create(createAnswer);
    }

    public @NonNull Answer update(@NonNull UUID answerId, @NonNull UpdateAnswer updateAnswer) {
        return answerClient.update(answerId, updateAnswer);
    }
}
