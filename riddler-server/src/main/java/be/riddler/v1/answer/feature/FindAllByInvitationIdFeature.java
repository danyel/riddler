package be.riddler.v1.answer.feature;

import be.riddler.v1.answer.client.model.Answer;

import java.util.List;
import java.util.UUID;

/**
 * FindAllByInvitationIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 12/07/2026
 */
public interface FindAllByInvitationIdFeature {
    List<Answer> findAllByInvitationId(UUID invitationId);
}
