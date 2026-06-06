package be.riddler.v1.settings.bff;

import be.riddler.v1.settings.client.SettingsClient;
import be.riddler.v1.settings.model.Settings;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import static lombok.AccessLevel.PACKAGE;

/**
 * SettingsEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = PACKAGE)
public class SettingsEndpoint {
    private final SettingsClient settingsClient;

    public @NonNull Settings getSettings() {
        return settingsClient.getSettings();
    }
}
