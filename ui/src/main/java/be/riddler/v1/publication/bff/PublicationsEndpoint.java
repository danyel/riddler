package be.riddler.v1.publication.bff;

import be.riddler.v1.publication.client.PublicationClient;
import be.riddler.v1.publication.client.model.CreateLevel;
import be.riddler.v1.publication.client.model.CreatePosition;
import be.riddler.v1.publication.client.model.CreatePublication;
import be.riddler.v1.publication.client.model.Level;
import be.riddler.v1.publication.client.model.Position;
import be.riddler.v1.publication.client.model.Publication;
import be.riddler.v1.publication.mapper.LevelMapper;
import be.riddler.v1.publication.mapper.PositionMapper;
import be.riddler.v1.publication.repository.LevelRepository;
import be.riddler.v1.publication.repository.PositionRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * PublicationsEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = PACKAGE)
public class PublicationsEndpoint {
    private final PublicationClient publicationClient;
    private final LevelRepository levelRepository;
    private final PositionRepository positionRepository;

    public @NonNull Publication createPublication(@NonNull CreatePublication createPublication) {
        return publicationClient.createPublication(createPublication);
    }

    public @NonNull List<@NonNull Position> getPositions() {
        return publicationClient.getPositions();
    }

    public @NonNull List<@NonNull Level> getLevels() {
        return publicationClient.getLevels();
    }

    public @NonNull List<@NonNull Publication> getPublications() {
        return publicationClient.getPublications();
    }

    public @NonNull Publication findPublicationById(@NonNull UUID publicationId) {
        return publicationClient.findPublicationById(publicationId);
    }

    public @NonNull Position createPosition(@NonNull CreatePosition createPosition) {
        return publicationClient.createPosition(createPosition);
    }

    public @NonNull Position findPositionById(UUID positionId) {
        return publicationClient.findPositionById(positionId);
    }

    public @NonNull Level createLevel(@NonNull CreateLevel createLevel) {
        return publicationClient.createLevel(createLevel);
    }

    public @NonNull List<@NonNull Level> findLevelsByLevel(@NonNull String level) {
        return levelRepository.findAllByLevelLikeIgnoreCase(level)
                .stream()
                .map(LevelMapper::fromEntity)
                .toList();
    }

    public @NonNull List<@NonNull Position> findPositionsByPosition(@NonNull String position) {
        return positionRepository.findAllByPositionLikeIgnoreCase(position)
                .stream()
                .map(PositionMapper::fromEntity)
                .toList();
    }

    public @NonNull Level findLevelById(@NonNull UUID levelId) {
        return publicationClient.findLevelById(levelId);
    }
}
