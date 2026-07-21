package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.Position;
import be.riddler.v1.publication.feature.FindPositionsByPositionFeature;
import be.riddler.v1.publication.mapper.PositionMapper;
import be.riddler.v1.publication.repository.PositionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * FindPositionsByPositionFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 12/07/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class FindPositionsByPositionFeatureImpl implements FindPositionsByPositionFeature {
    private final PositionRepository positionRepository;

    @Transactional
    @Override
    public @NonNull List<@NonNull Position> findPositionsByPosition(@NonNull String position) {
        return positionRepository.findAllByPositionLikeIgnoreCase(position)
                .stream()
                .map(PositionMapper::fromEntity)
                .toList();
    }
}
