package be.riddler.v1.participant.api;

import be.riddler.v1.participant.client.ParticipantClient;
import be.riddler.v1.participant.client.model.CreateParticipant;
import be.riddler.v1.participant.client.model.Participant;
import be.riddler.v1.participant.client.model.ParticipantId;
import be.riddler.v1.participant.feature.CreateParticipantFeature;
import be.riddler.v1.participant.feature.FindAllParticipantsFeature;
import be.riddler.v1.participant.feature.FindByParticipantIdFeature;
import be.riddler.v1.participant.feature.UploadCvFeature;
import be.riddler.v1.participant.feature.UploadPhotoFeature;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * ParticipantResource
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@RestController
@RequestMapping(path = ParticipantResource.BASE)
@RequiredArgsConstructor(access = PACKAGE)
class ParticipantResource implements ParticipantClient {
    public static final String BASE = "/v1/participants";
    private final FindByParticipantIdFeature findByParticipantIdFeature;
    private final FindAllParticipantsFeature findAllParticipantsFeature;
    private final CreateParticipantFeature createParticipantFeature;
    private final UploadPhotoFeature uploadPhotoFeature;
    private final UploadCvFeature uploadCvFeature;

    @Override
    public @NonNull List<@NonNull Participant> findAll() {
        return findAllParticipantsFeature.findAll();
    }

    @Override
    public @NonNull Participant findById(@NonNull UUID participantId) {
        return findByParticipantIdFeature.findByParticipantId(ParticipantId.from(participantId));
    }

    @Override
    public @NonNull Participant create(@NonNull CreateParticipant participantDetail) {
        return createParticipantFeature.create(participantDetail);
    }

    @PostMapping(path = "/{id}/photo")
    @ResponseStatus(HttpStatus.OK)
    public void uploadPhoto(@PathVariable(name = "id") UUID participantId, HttpServletRequest request) {
        try {
            uploadPhotoFeature.uploadPhoto(participantId, request.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping(path = "/{id}/cv")
    @ResponseStatus(HttpStatus.OK)
    public void uploadCv(@PathVariable(name = "id") UUID participantId, HttpServletRequest request) {
        try {
            uploadCvFeature.uploadCv(participantId, request.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
