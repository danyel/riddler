package be.riddler.v1.publication.repository;

import be.riddler.v1.publication.entity.PositionEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * PositionRepository
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public interface PositionRepository extends JpaRepository<PositionEntity, UUID> {
    List<PositionEntity> findAllByPositionLikeIgnoreCase(@NonNull String position);
}
