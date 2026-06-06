package be.riddler.v1.ui.bff;

import be.riddler.v1.ui.client.UiClient;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * QuestionTypeEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = PACKAGE)
public class QuestionTypeEndpoint {
    private final UiClient uiClient;

    public @NonNull List<@NonNull String> questionTypes() {
        return uiClient.questionTypes();
    }
}
