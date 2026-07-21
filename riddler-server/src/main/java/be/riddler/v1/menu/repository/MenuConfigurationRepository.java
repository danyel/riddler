package be.riddler.v1.menu.repository;

import be.riddler.v1.menu.entity.MenuConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * MenuConfigurationRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface MenuConfigurationRepository extends JpaRepository<MenuConfigurationEntity, UUID> {
    List<MenuConfigurationEntity> findAllByRoleIn(List<String> roles);
}
