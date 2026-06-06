package be.riddler.v1.publication.api;

import be.riddler.v1.publication.client.PublicationClient;
import be.riddler.v1.publication.client.model.CreateLevel;
import be.riddler.v1.publication.client.model.CreatePosition;
import be.riddler.v1.publication.client.model.CreatePublication;
import be.riddler.v1.publication.client.model.Level;
import be.riddler.v1.publication.client.model.Position;
import be.riddler.v1.publication.client.model.Publication;
import be.riddler.v1.publication.feature.CreateLevelFeature;
import be.riddler.v1.publication.feature.CreatePositionFeature;
import be.riddler.v1.publication.feature.CreatePublicationFeature;
import be.riddler.v1.publication.feature.FindAllLevelsFeature;
import be.riddler.v1.publication.feature.FindAllPositionsFeature;
import be.riddler.v1.publication.feature.FindAllPublicationsFeature;
import be.riddler.v1.publication.feature.FindLevelByIdFeature;
import be.riddler.v1.publication.feature.FindPositionByIdFeature;
import be.riddler.v1.publication.feature.FindPublicationByIdFeature;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * PublicationResource
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@RestController
@RequestMapping(path = "/v1/publications")
@RequiredArgsConstructor(access = PACKAGE)
class PublicationResource implements PublicationClient {
    private final FindAllPublicationsFeature findAllPublicationsFeature;
    private final FindPublicationByIdFeature findPublicationByIdFeature;
    private final CreatePublicationFeature createPublicationFeature;
    private final FindPositionByIdFeature findPositionByIdFeature;
    private final FindAllPositionsFeature findAllPositionsFeature;
    private final CreatePositionFeature createPositionFeature;
    private final FindLevelByIdFeature findLevelByIdFeature;
    private final FindAllLevelsFeature findAllLevelsFeature;
    private final CreateLevelFeature createLevelFeature;

    @Override
    public @NonNull List<@NonNull Publication> getPublications() {
        return findAllPublicationsFeature.findAllPublications();
    }

    @Override
    public @NonNull Publication findPublicationById(@NonNull UUID publicationId) {
        return findPublicationByIdFeature.findById(publicationId);
    }

    @Override
    public @NonNull Publication createPublication(@NonNull CreatePublication createPublication) {
        return createPublicationFeature.create(createPublication);
    }

    @Override
    public @NonNull Position createPosition(@NonNull CreatePosition createPosition) {
        return createPositionFeature.create(createPosition);
    }

    @Override
    public @NonNull Position findPositionById(@NonNull UUID positionId) {
        return findPositionByIdFeature.findById(positionId);
    }

    @Override
    public @NonNull List<@NonNull Position> getPositions() {
        return findAllPositionsFeature.findAllPositions();
    }

    @Override
    public @NonNull Level createLevel(@NonNull CreateLevel createLevel) {
        return createLevelFeature.create(createLevel);
    }

    @Override
    public @NonNull Level findLevelById(@NonNull UUID levelId) {
        return findLevelByIdFeature.findById(levelId);
    }

    @Override
    public @NonNull List<@NonNull Level> getLevels() {
        return findAllLevelsFeature.findAllLevels();
    }
}
