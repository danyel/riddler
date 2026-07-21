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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

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
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserInfo.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetMapping(path = "/{username}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    UserInfo authenticate(@PathVariable(name = "username") String username, @PathVariable(name = "password") String password);
}
