package be.riddler.v1.publication.client;

import be.riddler.common.header.Headers;
import be.riddler.common.tag.Tags;
import be.riddler.v1.publication.client.model.CreateLevel;
import be.riddler.v1.publication.client.model.CreatePosition;
import be.riddler.v1.publication.client.model.CreatePublication;
import be.riddler.v1.publication.client.model.Level;
import be.riddler.v1.publication.client.model.Position;
import be.riddler.v1.publication.client.model.Publication;
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
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * PublicationClient
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@Tag(name = Tags.PUBLICATIONS)
public interface PublicationClient {

    @Operation(
            method = "GET",
            tags = Tags.PUBLICATIONS,
            summary = "Retrieves all publication",
            operationId = "getPublications",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Publication.class))
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
    @GetExchange
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull Publication> getPublications();


    @Operation(
            method = "GET",
            tags = Tags.PUBLICATIONS,
            summary = "Retrieve the publication by id",
            operationId = "findById",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Publication.class)
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
    @GetExchange(url = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @NonNull Publication findPublicationById(@PathVariable(name = "id") @NonNull UUID publicationId);

    @Operation(
            method = "POST",
            tags = Tags.PUBLICATIONS,
            operationId = "createPublication",
            summary = "Creates a publication",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(
                            implementation = CreatePublication.class),
                            mediaType = APPLICATION_JSON_VALUE,
                            encoding = @Encoding(contentType = APPLICATION_JSON_VALUE
                            )
                    )

            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Publication.class)
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
    @PostExchange
    @ResponseStatus(HttpStatus.CREATED)
    @NonNull Publication createPublication(@RequestBody @NonNull CreatePublication createPublication);

    @Operation(
            method = "POST",
            tags = Tags.PUBLICATIONS,
            operationId = "createPosition",
            summary = "Creates a positions",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(
                            implementation = CreatePosition.class),
                            mediaType = APPLICATION_JSON_VALUE,
                            encoding = @Encoding(contentType = APPLICATION_JSON_VALUE
                            )
                    )

            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Position.class)
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
    @PostExchange(url = "/positions")
    @ResponseStatus(HttpStatus.CREATED)
    @NonNull Position createPosition(@RequestBody @NonNull CreatePosition createPosition);

    @Operation(
            method = "GET",
            tags = Tags.PUBLICATIONS,
            summary = "Retrieve the position by id",
            operationId = "findPositionById",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Position.class)
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
    @GetExchange(url = "/positions/{id}")
    @ResponseStatus(HttpStatus.OK)
    @NonNull Position findPositionById(@NonNull @PathVariable(name = "id") UUID positionId);

    @Operation(
            method = "GET",
            tags = Tags.PUBLICATIONS,
            summary = "Retrieves all positions",
            operationId = "findAllPositions",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Position.class))
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
    @GetExchange(url = "/positions")
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull Position> getPositions();

    @Operation(
            method = "POST",
            tags = Tags.PUBLICATIONS,
            operationId = "createLevel",
            summary = "Creates a level",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(
                            implementation = CreateLevel.class),
                            mediaType = APPLICATION_JSON_VALUE,
                            encoding = @Encoding(contentType = APPLICATION_JSON_VALUE
                            )
                    )

            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Level.class)
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
    @PostExchange(url = "/levels")
    @ResponseStatus(HttpStatus.CREATED)
    @NonNull Level createLevel(@RequestBody @NonNull CreateLevel createLevel);

    @Operation(
            method = "GET",
            tags = Tags.PUBLICATIONS,
            summary = "Retrieve the level by id",
            operationId = "findLevelById",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Level.class)
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
    @GetExchange(url = "/levels/{id}")
    @ResponseStatus(HttpStatus.OK)
    @NonNull Level findLevelById(@NonNull @PathVariable(name = "id") UUID levelId);

    @Operation(
            method = "GET",
            tags = Tags.PUBLICATIONS,
            summary = "Retrieve the level by id",
            operationId = "findLevelById",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Level.class)
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
    @GetExchange(url = "/levels/{level}")
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull Level> findLevelsByLevel(@PathVariable @NonNull String level);

    @Operation(
            method = "GET",
            tags = Tags.PUBLICATIONS,
            summary = "Retrieves levels",
            operationId = "findAllLevels",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Level.class))
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
    @GetExchange(url = "/levels")
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull Level> getLevels();

    @Operation(
            method = "GET",
            tags = Tags.PUBLICATIONS,
            summary = "Retrieves positions",
            operationId = "findAllLevels",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(name = "id", schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Level.class))
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
    @GetExchange(url = "/position/{id}/positions")
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull Position> findPositionsByPosition(@PathVariable("id") @NonNull String position);
}
