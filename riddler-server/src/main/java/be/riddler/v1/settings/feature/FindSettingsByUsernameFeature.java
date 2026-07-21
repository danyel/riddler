package be.riddler.v1.settings.feature;

import be.riddler.v1.settings.model.Settings;
import org.jspecify.annotations.NonNull;

/**
 * CreateBookmarkFeature
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
public interface FindSettingsByUsernameFeature {
    @NonNull Settings findSettingsByUsername();
}
