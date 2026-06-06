package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.Level;
import be.riddler.v1.publication.feature.FindAllLevelsFeature;
import be.riddler.v1.publication.mapper.LevelMapper;
import be.riddler.v1.publication.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindAllLevelsFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindAllLevelsFeatureImpl implements FindAllLevelsFeature {
    private final LevelRepository levelRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull List<@NonNull Level> findAllLevels() {
        return levelRepository.findAll()
                .stream()
                .map(LevelMapper::fromEntity)
                .toList();
    }
}
