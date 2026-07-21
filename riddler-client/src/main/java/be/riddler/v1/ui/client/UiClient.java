package be.riddler.v1.ui.client;

import be.riddler.common.header.Headers;
import be.riddler.common.tag.Tags;
import be.riddler.v1.ui.client.model.Translation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;
import java.util.UUID;

/**
 * UiApi
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Tag(name = Tags.UI)
public interface UiClient {

    @Operation(
            method = "GET",
            tags = Tags.UI,
            summary = "Retrieves all possible icon names",
            operationId = "icons",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/icons")
    @ResponseStatus(HttpStatus.OK)
    List<String> icons();

    @Operation(
            method = "GET",
            tags = Tags.UI,
            summary = "Retrieves all supported languages",
            operationId = "supportedLanguages",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/supported-languages")
    @ResponseStatus(HttpStatus.OK)
    List<String> supportedLanguages();

    @Operation(
            method = "GET",
            tags = Tags.UI,
            summary = "Retrieves all question types",
            operationId = "questionTypes",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/question-types")
    @ResponseStatus(HttpStatus.OK)
    List<String> questionTypes();

    @Operation(
            method = "GET",
            tags = Tags.UI,
            summary = "Retrieves translations for a specific language",
            operationId = "translations",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(in = ParameterIn.QUERY, name = "language", schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Translation.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/translations")
    @ResponseStatus(HttpStatus.OK)
    List<Translation> translations(@RequestParam(name = "language") String language);

    @Operation(
            method = "GET",
            tags = Tags.UI,
            summary = "Retrieves translations for a specific language",
            operationId = "translate",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class)),
                    @Parameter(in = ParameterIn.QUERY, name = "language", schema = @Schema(implementation = String.class)),
                    @Parameter(in = ParameterIn.QUERY, name = "key", schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Translation.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/translate")
    @ResponseStatus(HttpStatus.OK)
    Translation translate(@RequestParam(name = "language") String language, @RequestParam(name = "key") String key);
}
