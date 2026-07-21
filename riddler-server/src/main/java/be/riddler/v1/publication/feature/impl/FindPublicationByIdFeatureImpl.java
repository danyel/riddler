package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.Publication;
import be.riddler.v1.publication.feature.FindPublicationByIdFeature;
import be.riddler.v1.publication.mapper.PublicationMapper;
import be.riddler.v1.publication.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindPublicationByIdFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindPublicationByIdFeatureImpl implements FindPublicationByIdFeature {
    private final PublicationRepository publicationRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull Publication findById(@NonNull UUID publicationId) {
        return publicationRepository.findById(publicationId)
                .map(PublicationMapper::fromEntity)
                .orElseThrow();
    }
}
