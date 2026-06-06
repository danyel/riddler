package be.riddler.v1.settings.feature.impl;

import be.riddler.v1.common.http.BadRequest;
import be.riddler.v1.settings.constants.UserContext;
import be.riddler.v1.settings.entity.BookmarkEntity;
import be.riddler.v1.settings.entity.SettingsEntity;
import be.riddler.v1.settings.feature.CreateBookmarkFeature;
import be.riddler.v1.settings.mapper.SettingsMapper;
import be.riddler.v1.settings.model.CreateBookmark;
import be.riddler.v1.settings.model.Settings;
import be.riddler.v1.settings.repository.BookmarkRepository;
import be.riddler.v1.settings.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreateBookmarkFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class CreateBookmarkFeatureImpl implements CreateBookmarkFeature {
    private final SettingsRepository settingsRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    @Override
    public @NonNull Settings createBookmark(@NonNull CreateBookmark createBookmark) {
        var settingsEntity = settingsRepository.findByUsername(UserContext.username());
        SettingsEntity settings = settingsEntity.orElseGet(() -> SettingsEntity.builder()
                .username(UserContext.username())
                .build());

        if (settings.getBookmarks()
                .stream()
                .anyMatch(bookmarkEntity ->
                        bookmarkEntity.getBookmarkType().equals(createBookmark.bookmarkType()) &&
                                bookmarkEntity.getPath().equals(createBookmark.path()) &&
                                bookmarkEntity.getLabel().equals(createBookmark.label()))
        ) {
            throw new BadRequest(createBookmark, "Bookmark already exists");
        }

        var bookmarkEntity = BookmarkEntity.builder()
                .bookmarkType(createBookmark.bookmarkType())
                .label(createBookmark.label())
                .path(createBookmark.path())
                .settings(settings)
                .build();
        bookmarkRepository.save(bookmarkEntity);
        settings.getBookmarks().add(bookmarkEntity);

        settingsRepository.save(settings);
        return SettingsMapper.fromSettingsEntity(settings, UserContext.roles());
    }
}
