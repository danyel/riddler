package be.riddler.v1.invitation.client;

import be.riddler.common.header.Headers;
import be.riddler.common.tag.Tags;
import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.client.model.UpdateInvitation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.UUID;

/**
 * InvitationApi
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Tag(name = Tags.INVITATIONS)
public interface InvitationClient {

    @Operation(
            method = "POST",
            tags = Tags.INVITATIONS,
            operationId = "create",
            summary = "Creates an invitation",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = CreateInvitation.class), mediaType = MediaType.APPLICATION_JSON_VALUE, encoding = @Encoding(contentType = MediaType.APPLICATION_JSON_VALUE))

            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Invitation.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostExchange
    @ResponseStatus(HttpStatus.CREATED)
    @NonNull Invitation create(@RequestBody @NonNull CreateInvitation createInvitation);

    @Operation(
            method = "PUT",
            tags = Tags.INVITATIONS,
            operationId = "update",
            summary = "Updates an invitation",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UpdateInvitation.class), mediaType = MediaType.APPLICATION_JSON_VALUE, encoding = @Encoding(contentType = MediaType.APPLICATION_JSON_VALUE))

            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Invitation.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PutExchange(url = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @NonNull Invitation update(@PathVariable(name = "id") UUID invitationId, @RequestBody @NonNull UpdateInvitation updateInvitation);

    @Operation(
            method = "GET",
            tags = Tags.INVITATIONS,
            summary = "Retrieve the invitation corresponding to the id",
            operationId = "findById",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Invitation.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @NonNull Invitation findById(@PathVariable(name = "id") @NonNull UUID invitationId);

    @Operation(
            method = "GET",
            tags = Tags.INVITATIONS,
            summary = "Retrieves the invitations corresponding to the participant id",
            operationId = "findInvitationsByParticipantId",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Invitation.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/participants/{id}")
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull Invitation> findInvitationsByParticipantId(@PathVariable(name = "id") @NonNull UUID participantId);

    @Operation(
            method = "DELETE",
            tags = Tags.INVITATIONS,
            summary = "Deletes the invitation corresponding to the id",
            operationId = "deleteById",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @DeleteExchange(url = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable(name = "id") @NonNull UUID invitationId);

    @Operation(
            method = "GET",
            tags = Tags.INVITATIONS,
            summary = "Generates a token",
            operationId = "generateToken",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "{id}/generate")
    @ResponseStatus(HttpStatus.CREATED)
    @NonNull Invitation generateToken(@PathVariable(name = "id") @NonNull UUID invitationId);

    @Operation(
            method = "GET",
            tags = Tags.INVITATIONS,
            summary = "Finds the token",
            operationId = "findByToken",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Invitation.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/token/{id}")
    @ResponseStatus(HttpStatus.OK)
    @NonNull Invitation findByToken(@PathVariable(name = "id") @NonNull String token);
}
