package be.riddler.v1.participant.repository;

import be.riddler.v1.participant.entity.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * ParticipantRepository
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public interface ParticipantRepository extends JpaRepository<ParticipantEntity, UUID> {
}
