package be.riddler.v1.participant.client;

import be.riddler.common.header.Headers;
import be.riddler.v1.participant.client.model.CreateParticipant;
import be.riddler.v1.participant.client.model.Participant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * ParticipantApi
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public interface ParticipantClient {
    @Operation(
            method = "GET",
            tags = "participants",
            summary = "Retrieve the participant corresponding to the id",
            operationId = "findById",
            parameters = {
                    @Parameter(name = "id", schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Participant.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @NonNull Participant findById(@PathVariable(name = "id") @NonNull UUID participantId);

    @Operation(
            method = "GET",
            tags = "participants",
            summary = "Retrieve all participants",
            operationId = "findAll",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Participant.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull Participant> findAll();

    @Operation(
            method = "POST",
            tags = "participants",
            operationId = "create",
            summary = "Creates a participant",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = CreateParticipant.class), mediaType = MediaType.APPLICATION_JSON_VALUE, encoding = @Encoding(contentType = MediaType.APPLICATION_JSON_VALUE))

            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Participant.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @NonNull Participant create(@RequestBody @NonNull CreateParticipant participantDetail);
}
