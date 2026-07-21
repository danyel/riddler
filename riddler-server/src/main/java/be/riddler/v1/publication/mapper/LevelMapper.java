package be.riddler.v1.publication.mapper;

import be.riddler.v1.publication.client.model.CreateLevel;
import be.riddler.v1.publication.client.model.Level;
import be.riddler.v1.publication.entity.LevelEntity;
import lombok.experimental.UtilityClass;

/**
 * LevelMapper
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@UtilityClass
public class LevelMapper {
    public static Level fromEntity(LevelEntity e) {
        return new Level(e.getId(), e.getLevel());
    }

    public static LevelEntity fromCreateLevel(CreateLevel e) {
        return LevelEntity.builder()
                .level(e.level())
                .build();
    }
}
