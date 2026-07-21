package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.CreatePublication;
import be.riddler.v1.publication.client.model.Publication;
import be.riddler.v1.publication.feature.CreatePublicationFeature;
import be.riddler.v1.publication.mapper.PublicationMapper;
import be.riddler.v1.publication.repository.LevelRepository;
import be.riddler.v1.publication.repository.PositionRepository;
import be.riddler.v1.publication.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreatePublicationFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class CreatePublicationFeatureImpl implements CreatePublicationFeature {
    private final PublicationRepository publicationRepository;
    private final LevelRepository levelRepository;
    private final PositionRepository positionRepository;

    @Transactional
    @Override
    public @NonNull Publication create(@NonNull CreatePublication createPublication) {
        var publicationEntity = PublicationMapper.fromCreatePublication(createPublication);
        positionRepository.findById(publicationEntity.getPosition().getId())
                .ifPresent(publicationEntity::setPosition);
        levelRepository.findById(publicationEntity.getLevel().getId())
                .ifPresent(publicationEntity::setLevel);
        return PublicationMapper.fromEntity(publicationRepository.save(publicationEntity));
    }
}
