package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.Position;
import be.riddler.v1.publication.feature.FindPositionByIdFeature;
import be.riddler.v1.publication.mapper.PositionMapper;
import be.riddler.v1.publication.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindPositionByIdFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindPositionByIdFeatureImpl implements FindPositionByIdFeature {
    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull Position findById(UUID positionId) {
        return positionRepository.findById(positionId)
                .map(PositionMapper::fromEntity)
                .orElseThrow();
    }
}
