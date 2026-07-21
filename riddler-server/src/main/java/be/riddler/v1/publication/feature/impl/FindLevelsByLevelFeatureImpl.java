package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.Level;
import be.riddler.v1.publication.feature.FindLevelsByLevelFeature;
import be.riddler.v1.publication.mapper.LevelMapper;
import be.riddler.v1.publication.repository.LevelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FindLevelsByLevelFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 12/07/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class FindLevelsByLevelFeatureImpl implements FindLevelsByLevelFeature {
    private final LevelRepository levelRepository;

    @Override
    public @NonNull List<@NonNull Level> findLevelsByLevel(@NonNull String level) {
        return levelRepository.findAllByLevelLikeIgnoreCase(level)
                .stream()
                .map(LevelMapper::fromEntity)
                .toList();
    }
}
