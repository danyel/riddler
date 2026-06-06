package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.CreatePosition;
import be.riddler.v1.publication.client.model.Position;
import be.riddler.v1.publication.feature.CreatePositionFeature;
import be.riddler.v1.publication.mapper.PositionMapper;
import be.riddler.v1.publication.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreatePositionFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class CreatePositionFeatureImpl implements CreatePositionFeature {
    private final PositionRepository positionRepository;

    @Transactional
    @Override
    public @NonNull Position create(@NonNull CreatePosition createPosition) {
        var positionEntity = PositionMapper.fromCreatePosition(createPosition);
        return PositionMapper.fromEntity(positionRepository.save(positionEntity));
    }
}
