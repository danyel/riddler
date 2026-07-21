package be.riddler.v1.participant.api;

import be.riddler.common.path.Paths;
import be.riddler.common.resource.AbstractResourceIntegrationTest;
import be.riddler.v1.participant.client.model.CreateParticipant;
import be.riddler.v1.participant.client.model.Participant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.MultipartBodyBuilder;

import java.io.IOException;

import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

/**
 * ParticipantResourceIntegrationTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Participant Resource Integration Test")
class ParticipantResourceIntegrationTest extends AbstractResourceIntegrationTest {
    @DisplayName("1 Find All")
    @Test
    void findAll() {
        var participants = doExchange(getWebTestClient().get()
                .uri(Paths.PARTICIPANT))
                .expectStatus()
                .isOk()
                .expectBody(Participant[].class)
                .returnResult()
                .getResponseBody();

        assertThat(participants).isNotNull();
        assertThat(participants).isNotEmpty();
        assertThat(participants).hasSize(1);
    }

    @DisplayName("2 Find By Id")
    @Test
    void findById() {
        var participant = doExchange(getWebTestClient().get()
                .uri("%s/%s".formatted(Paths.PARTICIPANT, PARTICIPANT_ID)))
                .expectStatus()
                .isOk()
                .expectBody(Participant.class)
                .returnResult()
                .getResponseBody();
        assertThat(participant).isNotNull();
        assertThat(participant).isInstanceOf(Participant.class);
        assertThat(participant.id()).isEqualTo(PARTICIPANT_ID);
        assertThat(participant.firstName()).isEqualTo("Daniel");
    }

    @DisplayName("3 Create participant")
    @Test
    void create() {
        var participant = doExchange(getWebTestClient().post()
                .uri(Paths.PARTICIPANT)
                .contentType(APPLICATION_JSON)
                .bodyValue(new CreateParticipant("first", "last", "email")))
                .expectStatus()
                .isCreated()
                .expectBody(Participant.class)
                .returnResult()
                .getResponseBody();

        assertThat(participant).isNotNull();
        assertThat(participant.firstName()).isEqualTo("first");
        assertThat(participant.lastName()).isEqualTo("last");
        assertThat(participant.emailAddress()).isEqualTo("email");
    }

    @DisplayName("4 Upload Photo")
    @Test
    void uploadPhoto() {
        byte[] mockPhotoBytes = "fake-photo-bytes-content".getBytes();
        var bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", mockPhotoBytes)
                .filename("profile.jpg")
                .contentType(IMAGE_JPEG);

        doExchange(getWebTestClient().post()
                .uri("%s/%s/photo".formatted(Paths.PARTICIPANT, PARTICIPANT_ID))
                .contentType(MULTIPART_FORM_DATA)
                .bodyValue(bodyBuilder.build()))
                .expectStatus()
                .isOk();
    }

    @DisplayName("5 Upload Cv")
    @Test
    void uploadCv() {
        try {
            byte[] mockCvBytes = new ClassPathResource("Fake-Resume.pdf").getContentAsByteArray();
            doExchange(getWebTestClient().post()
                    .uri("%s/%s/cv".formatted(Paths.PARTICIPANT, PARTICIPANT_ID))
                    .contentType(APPLICATION_PDF)
                    .bodyValue(mockCvBytes))
                    .expectStatus()
                    .isOk();
        } catch (IOException e) {
            fail("Could not read mock PDF file", e);
        }
    }
}