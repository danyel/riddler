package be.riddler.v1.settings.feature;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * CreateBookmarkFeature
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
public interface DeleteBookmarkByIdFeature {
    void deleteBookmark(@NonNull UUID bookmarkId);
}
