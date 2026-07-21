package be.riddler.v1.invitation.repository;

import be.riddler.v1.invitation.entity.InvitationEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * InvitationRepository
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface InvitationRepository extends JpaRepository<InvitationEntity, UUID> {
    List<InvitationEntity> findAllByParticipantId(@NonNull UUID participantId);

    Optional<InvitationEntity> findByStoredToken(String storedToken);
}
