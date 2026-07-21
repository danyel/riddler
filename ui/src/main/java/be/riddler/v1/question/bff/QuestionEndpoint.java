package be.riddler.v1.question.bff;

import be.riddler.v1.question.client.QuestionClient;
import be.riddler.v1.question.client.model.AddOption;
import be.riddler.v1.question.client.model.CreateQuestion;
import be.riddler.v1.question.client.model.Option;
import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.client.model.UpdateOption;
import be.riddler.v1.question.client.model.UpdateQuestion;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * QuestionService
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@BrowserCallable
@RolesAllowed("ADMIN")
@RequiredArgsConstructor(access = PACKAGE)
public class QuestionEndpoint {
    private final QuestionClient questionClient;

    public @NonNull List<@NonNull Question> getQuestions() {
        return questionClient.getQuestionsById(List.of());
    }

    public @NonNull Question get(UUID uuid) {
        return questionClient.findById(uuid);
    }

    public @NonNull Question update(UUID id, UpdateQuestion updateQuestion) {
        return questionClient.update(id, updateQuestion);
    }

    public void delete(@NonNull UUID uuid) {
        questionClient.delete(uuid);
    }

    public Question create(CreateQuestion createQuestion) {
        return questionClient.create(createQuestion);
    }

    @Deprecated
    public @NonNull Option addOption(@NonNull AddOption addOption) {
        return questionClient.addOption(addOption);
    }

    @Deprecated
    public @NonNull Option updateOption(@NonNull UpdateOption updateOption) {
        return questionClient.updateOption(updateOption);
    }
}
