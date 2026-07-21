package be.riddler.v1.publication.repository;

import be.riddler.v1.publication.entity.PublicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

/**
 * PublicationEntity
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public interface PublicationRepository extends JpaRepository<PublicationEntity, UUID>, JpaSpecificationExecutor<PublicationEntity> {
}
