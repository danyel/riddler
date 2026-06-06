package be.riddler.v1.publication.feature.impl;

import be.riddler.v1.publication.client.model.Publication;
import be.riddler.v1.publication.feature.FindAllPublicationsFeature;
import be.riddler.v1.publication.mapper.PublicationMapper;
import be.riddler.v1.publication.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindAllPublicationsFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindAllPublicationsFeatureImpl implements FindAllPublicationsFeature {
    private final PublicationRepository publicationRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull List<@NonNull Publication> findAllPublications() {
        return publicationRepository.findAll()
                .stream()
                .map(PublicationMapper::fromEntity)
                .toList();
    }
}
