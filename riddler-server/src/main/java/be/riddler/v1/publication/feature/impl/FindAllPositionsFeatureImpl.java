package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.Position;
import be.riddler.v1.publication.feature.FindAllPositionsFeature;
import be.riddler.v1.publication.mapper.PositionMapper;
import be.riddler.v1.publication.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindAllPositionsFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindAllPositionsFeatureImpl implements FindAllPositionsFeature {
    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull List<Position> findAllPositions() {
        return positionRepository.findAll()
                .stream()
                .map(PositionMapper::fromEntity)
                .toList();
    }
}
