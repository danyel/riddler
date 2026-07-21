package be.riddler.v1.bookmark.bff;

import be.riddler.v1.settings.client.SettingsClient;
import be.riddler.v1.settings.model.BookmarkType;
import be.riddler.v1.settings.model.CreateBookmark;
import be.riddler.v1.settings.model.Settings;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * BookmarkEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = PACKAGE)
public class BookmarkEndpoint {
    private final SettingsClient settingsClient;

    public @NonNull Settings bookmark(@NonNull CreateBookmark createBookmark) {
        return settingsClient.createBookmark(createBookmark);
    }

    public void deleteBookmark(@NonNull UUID bookmarkId) {
        settingsClient.deleteBookmark(bookmarkId);
    }

    public @NonNull List<@NonNull String> bookmarkTypes() {
        return EnumSet.allOf(BookmarkType.class)
                .stream()
                .map(Enum::name)
                .toList();
    }
}
