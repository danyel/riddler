package be.riddler.v1.security.client;

import be.riddler.common.tag.Tags;
import be.riddler.v1.security.client.model.UserInfo;
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
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

/**
 * SecurityApi
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Tag(name = Tags.SECURITY)
public interface SecurityClient {

    @SuppressWarnings("SpringMvcPathVariableDeclarationInspection")
    @Operation(
            method = "GET",
            tags = Tags.SECURITY,
            summary = "Authenticates the user",
            operationId = "authenticate",
            parameters = {
                    @Parameter(name = "username", in = ParameterIn.PATH),
                    @Parameter(name = "password", in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserInfo.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/{username}/{password}")
    @ResponseStatus(HttpStatus.OK)
    UserInfo authenticate(@PathVariable(name = "username") String username, @PathVariable(name = "password") String password);

    @Operation(
            method = "GET",
            tags = Tags.SECURITY,
            summary = "Retrieves the roles of the logged in user",
            operationId = "getAuthorities",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/roles")
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull String> getAuthorities();

    @Operation(
            method = "GET",
            tags = Tags.SECURITY,
            summary = "Authenticates the user",
            operationId = "getAuthenticatedUser",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserInfo.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange
    @ResponseStatus(HttpStatus.OK)
    UserInfo getAuthenticatedUser();
}
