package be.riddler.v1.publication.mapper;

import be.riddler.v1.publication.client.model.CreatePublication;
import be.riddler.v1.publication.client.model.Publication;
import be.riddler.v1.publication.entity.LevelEntity;
import be.riddler.v1.publication.entity.PositionEntity;
import be.riddler.v1.publication.entity.PublicationEntity;
import lombok.experimental.UtilityClass;

/**
 * PublicationMapper
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@UtilityClass
public class PublicationMapper {
    public static Publication fromEntity(PublicationEntity publicationEntity) {
        return new Publication(
                publicationEntity.getId(),
                publicationEntity.getTitle(),
                publicationEntity.getDescription(),
                publicationEntity.getProposal(),
                PositionMapper.fromEntity(publicationEntity.getPosition()),
                LevelMapper.fromEntity(publicationEntity.getLevel())
        );
    }

    public static PublicationEntity fromCreatePublication(CreatePublication createPublication) {
        var level = new LevelEntity();
        level.setId(createPublication.levelId());
        var position = new PositionEntity();
        position.setId(createPublication.positionId());
        return PublicationEntity.builder()
                .position(position)
                .level(level)
                .title(createPublication.title())
                .description(createPublication.description())
                .proposal(createPublication.proposal())
                .build();
    }
}
