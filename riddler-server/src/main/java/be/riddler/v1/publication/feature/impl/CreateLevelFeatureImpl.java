package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.CreateLevel;
import be.riddler.v1.publication.client.model.Level;
import be.riddler.v1.publication.feature.CreateLevelFeature;
import be.riddler.v1.publication.mapper.LevelMapper;
import be.riddler.v1.publication.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreateLevelFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class CreateLevelFeatureImpl implements CreateLevelFeature {
    private final LevelRepository levelRepository;

    @Transactional
    @Override
    public @NonNull Level create(@NonNull CreateLevel createLevel) {
        var levelEntity = LevelMapper.fromCreateLevel(createLevel);
        return LevelMapper.fromEntity(levelRepository.save(levelEntity));
    }
}
