package be.riddler.v1.ui.repository;

import be.riddler.v1.ui.entity.KeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * KeyRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface KeyRepository extends JpaRepository<KeyEntity, UUID> {
    KeyEntity findByKey(String key);
}
