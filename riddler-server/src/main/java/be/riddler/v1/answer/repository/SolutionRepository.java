package be.riddler.v1.answer.repository;

import be.riddler.v1.answer.entity.SolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * SolutionRepository
 *
 * @author dnoulet
 * @version 1.0.0 25/05/2026
 */
public interface SolutionRepository extends JpaRepository<SolutionEntity, UUID> {
}
