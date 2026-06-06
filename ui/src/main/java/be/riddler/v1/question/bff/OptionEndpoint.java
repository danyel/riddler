package be.riddler.v1.question.bff;

import be.riddler.v1.question.client.QuestionClient;
import be.riddler.v1.question.client.model.AddOption;
import be.riddler.v1.question.client.model.Option;
import be.riddler.v1.question.client.model.UpdateOption;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import static lombok.AccessLevel.PACKAGE;

/**
 * OptionEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 29/05/2026
 */
@BrowserCallable
@RolesAllowed("ADMIN")
@RequiredArgsConstructor(access = PACKAGE)
public class OptionEndpoint {
    private final QuestionClient questionApi;

    public @NonNull Option addOption(@NonNull AddOption addOption) {
        return questionApi.addOption(addOption);
    }

    public @NonNull Option updateOption(@NonNull UpdateOption updateOption) {
        return questionApi.updateOption(updateOption);
    }
}
