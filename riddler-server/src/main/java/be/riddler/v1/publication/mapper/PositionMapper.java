package be.riddler.v1.publication.mapper;

import be.riddler.v1.publication.client.model.CreatePosition;
import be.riddler.v1.publication.client.model.Position;
import be.riddler.v1.publication.entity.PositionEntity;
import lombok.experimental.UtilityClass;

/**
 * PositionMapper
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@UtilityClass
public class PositionMapper {
    public static Position fromEntity(PositionEntity positionEntity) {
        return new Position(positionEntity.getId(), positionEntity.getPosition());
    }

    public static PositionEntity fromCreatePosition(CreatePosition createPosition) {
        return PositionEntity.builder()
                .position(createPosition.position())
                .build();
    }
}
