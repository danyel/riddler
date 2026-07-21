package be.riddler.v1.menu.repository;

import be.riddler.v1.menu.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * MenuJpaRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface MenuRepository extends JpaRepository<MenuEntity, UUID> {
}
