package be.riddler.v1.settings.client;

import be.riddler.v1.settings.model.CreateBookmark;
import be.riddler.v1.settings.model.Settings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
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

import java.util.UUID;

/**
 * SettingsApi
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Tag(name = "settings")
public interface SettingsClient {
    @Operation(
            method = "GET",
            tags = "settings",
            summary = "Retrieves the settings of the logged in user",
            operationId = "getSettings",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Settings.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange
    @ResponseStatus(HttpStatus.OK)
    @NonNull Settings getSettings();

    @Operation(
            method = "POST",
            tags = "settings",
            summary = "Creates a bookmark for the current settings",
            operationId = "createBookmark",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(schema = @Schema(implementation = CreateBookmark.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Settings.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostExchange(accept = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @NonNull Settings createBookmark(@NonNull @RequestBody CreateBookmark createBookmark);

    @Operation(
            method = "DELETE",
            tags = "settings",
            summary = "Deletes a bookmark",
            operationId = "deleteBookmark",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "id",
                            schema = @Schema(implementation = String.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Settings.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @DeleteExchange(url = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBookmark(@NonNull @PathVariable(name = "id") UUID bookmarkId);
}
