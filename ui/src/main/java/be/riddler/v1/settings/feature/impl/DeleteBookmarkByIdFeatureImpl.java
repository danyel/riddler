package be.riddler.v1.settings.feature.impl;

import be.riddler.v1.settings.feature.DeleteBookmarkByIdFeature;
import be.riddler.v1.settings.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * DeleteBookmarkByIdFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
public class DeleteBookmarkByIdFeatureImpl implements DeleteBookmarkByIdFeature {
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    @Override
    public void deleteBookmark(@NonNull UUID bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }
}
