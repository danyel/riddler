package be.riddler.v1.question.repository;

import be.riddler.v1.question.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * OptionRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/05/2026
 */
public interface OptionRepository extends JpaRepository<OptionEntity, UUID> {
}
