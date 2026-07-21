package be.riddler.v1.settings.mapper;

import be.riddler.v1.settings.entity.SettingsEntity;
import be.riddler.v1.settings.model.Bookmark;
import be.riddler.v1.settings.model.Settings;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * SettingsMapper
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@UtilityClass
public class SettingsMapper {
    public static SettingsEntity m() {
        return new SettingsEntity();
    }

    public static Settings fromSettingsEntity(SettingsEntity settingsEntity, List<String> roles) {
        return new Settings(settingsEntity.getUsername(), roles, settingsEntity.getBookmarks()
                .stream()
                .map(bookmarkEntity -> new Bookmark(bookmarkEntity.getId(), bookmarkEntity.getBookmarkType(), bookmarkEntity.getPath(), bookmarkEntity.getLabel()))
                .toList());
    }
}
