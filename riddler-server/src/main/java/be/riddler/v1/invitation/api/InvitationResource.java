package be.riddler.v1.invitation.api;

import be.riddler.common.path.Paths;
import be.riddler.v1.invitation.client.InvitationClient;
import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.client.model.UpdateInvitation;
import be.riddler.v1.invitation.feature.DeleteByIdFeature;
import be.riddler.v1.invitation.feature.FindAllByParticipantIdFeature;
import be.riddler.v1.invitation.feature.FindByIdFeature;
import be.riddler.v1.invitation.feature.FindByTokenFeature;
import be.riddler.v1.invitation.feature.GenerateTokenFeature;
import be.riddler.v1.invitation.feature.SaveInvitationFeature;
import be.riddler.v1.invitation.feature.UpdateInvitationFeature;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * InvitationResource
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@RestController
@HttpExchange(url = Paths.INVITATION)
@RequiredArgsConstructor(access = PACKAGE)
class InvitationResource implements InvitationClient {
    private final SaveInvitationFeature saveInvitationFeature;
    private final FindByIdFeature findByIdFeature;
    private final DeleteByIdFeature deleteByIdFeature;
    private final FindAllByParticipantIdFeature findAllByParticipantIdFeature;
    private final UpdateInvitationFeature updateInvitationFeature;
    private final GenerateTokenFeature generateTokenFeature;
    private final FindByTokenFeature findByTokenFeature;

    @Override
    public @NonNull Invitation create(@NonNull CreateInvitation createInvitation) {
        return saveInvitationFeature.save(createInvitation);
    }

    @Override
    public @NonNull Invitation update(UUID invitationId, @NonNull UpdateInvitation updateInvitation) {
        return updateInvitationFeature.updateInvitation(invitationId, updateInvitation);
    }

    @Override
    public @NonNull Invitation findById(@NonNull UUID invitationId) {
        return findByIdFeature.findById(invitationId);
    }

    @Override
    public void deleteById(@NonNull UUID invitationId) {
        deleteByIdFeature.deleteById(invitationId);
    }

    @Override
    public @NonNull Invitation generateToken(@NonNull UUID invitationId) {
        return generateTokenFeature.generate(invitationId);
    }

    @Override
    public @NonNull List<@NonNull Invitation> findInvitationsByParticipantId(@NonNull UUID participantId) {
        return findAllByParticipantIdFeature.findAllByParticipantId(participantId);
    }

    @Override
    public @NonNull Invitation findByToken(@NonNull String token) {
        return findByTokenFeature.findByToken(token);
    }
}
