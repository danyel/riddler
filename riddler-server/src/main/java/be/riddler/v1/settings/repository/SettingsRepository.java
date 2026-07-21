package be.riddler.v1.settings.repository;

import be.riddler.v1.settings.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * SettingsRepository
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
public interface SettingsRepository extends JpaRepository<SettingsEntity, UUID> {
    Optional<SettingsEntity> findByUsername(String username);
}
