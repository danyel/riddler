package be.riddler.v1.ui.bff;

import be.riddler.v1.ui.client.UiClient;
import be.riddler.v1.ui.client.model.Translation;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

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
public class TranslateEndpoint {
    private final UiClient uiClient;

    public @NonNull Translation translate(String language, String key) {
        return uiClient.translate(language, key);
    }
}
