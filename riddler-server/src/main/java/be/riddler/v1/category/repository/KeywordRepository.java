package be.riddler.v1.category.repository;

import be.riddler.v1.category.entity.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * CategoryRepository
 *
 * @author dnoulet
 * @version 1.0.0 16/05/2026
 */
public interface KeywordRepository extends JpaRepository<KeywordEntity, UUID> {
}
