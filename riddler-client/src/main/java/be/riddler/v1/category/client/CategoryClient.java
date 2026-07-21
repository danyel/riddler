package be.riddler.v1.category.client;

import be.riddler.common.header.Headers;
import be.riddler.common.tag.Tags;
import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.CreateCategory;
import be.riddler.v1.category.client.model.UpdateCategory;
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
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.UUID;

/**
 * CategoryClient
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@Tag(name = Tags.CATEGORIES)
public interface CategoryClient {
    @Operation(
            method = "GET",
            tags = Tags.CATEGORIES,
            summary = "Retrieves the categories",
            operationId = "findAll",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Category.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull Category> findAll();

    @Operation(
            method = "POST",
            tags = Tags.CATEGORIES,
            summary = "Creates a category",
            operationId = "create",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(schema = @Schema(implementation = CreateCategory.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Category.class)))
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
    @NonNull Category create(@NonNull @RequestBody CreateCategory createCategory);

    @Operation(
            method = "DELETE",
            tags = Tags.CATEGORIES,
            summary = "Deletes a category by category id",
            operationId = "delete",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204"
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
    @DeleteExchange(url = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@NonNull @PathVariable(name = "id") UUID categoryId);

    @Operation(
            method = "PUT",
            tags = Tags.CATEGORIES,
            summary = "Updates a category by category id",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            operationId = "update",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UpdateCategory.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "204"
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
    @NonNull Category update(@NonNull @PathVariable(name = "id") UUID categoryId, @RequestBody @NonNull UpdateCategory updateCategory);
}
