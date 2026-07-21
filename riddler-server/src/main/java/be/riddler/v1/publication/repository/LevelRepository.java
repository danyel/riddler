package be.riddler.v1.publication.repository;

import be.riddler.v1.publication.entity.LevelEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * LevelRepository
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public interface LevelRepository extends JpaRepository<LevelEntity, UUID> {
    List<LevelEntity> findAllByLevelLikeIgnoreCase(@NonNull String level);
}
