package be.riddler.v1.settings.api;

import be.riddler.v1.settings.client.SettingsClient;
import be.riddler.v1.settings.feature.CreateBookmarkFeature;
import be.riddler.v1.settings.feature.DeleteBookmarkByIdFeature;
import be.riddler.v1.settings.feature.FindSettingsByUsernameFeature;
import be.riddler.v1.settings.model.CreateBookmark;
import be.riddler.v1.settings.model.Settings;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * SettingsResource
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@RestController
@RequestMapping(path = "/v1/settings")
@RequiredArgsConstructor(access = PACKAGE)
class SettingsResource implements SettingsClient {
    private final FindSettingsByUsernameFeature findSettingsByUsernameFeature;
    private final CreateBookmarkFeature createBookmarkFeature;
    private final DeleteBookmarkByIdFeature deleteBookmarkFeature;

    @Override
    public @NonNull Settings getSettings() {
        return findSettingsByUsernameFeature.findSettingsByUsername();
    }

    @Override
    public @NonNull Settings createBookmark(@NonNull CreateBookmark createBookmark) {
        return createBookmarkFeature.createBookmark(createBookmark);
    }

    @Override
    public void deleteBookmark(@NonNull UUID bookmarkId) {
        deleteBookmarkFeature.deleteBookmark(bookmarkId);
    }
}
