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
    private final QuestionClient questionApi;

    public @NonNull List<@NonNull Question> getQuestions() {
        return questionApi.getQuestionsById(List.of());
    }

    public @NonNull Question get(UUID uuid) {
        return questionApi.findById(uuid);
    }

    public @NonNull Question update(UUID id, UpdateQuestion updateQuestion) {
        return questionApi.update(id, updateQuestion);
    }

    public void delete(@NonNull UUID uuid) {
        questionApi.delete(uuid);
    }

    public Question create(CreateQuestion createQuestion) {
        return questionApi.create(createQuestion);
    }

    @Deprecated
    public @NonNull Option addOption(@NonNull AddOption addOption) {
        return questionApi.addOption(addOption);
    }

    @Deprecated
    public @NonNull Option updateOption(@NonNull UpdateOption updateOption) {
        return questionApi.updateOption(updateOption);
    }
}
