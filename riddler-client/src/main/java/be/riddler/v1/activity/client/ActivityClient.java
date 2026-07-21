package be.riddler.v1.activity.client;

import be.riddler.common.tag.Tags;
import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.CreateActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.UUID;

/**
 * ActivityApi
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Tag(name = Tags.ACTIVITIES)
public interface ActivityClient {

    @Operation(
            method = "GET",
            tags = Tags.ACTIVITIES,
            summary = "Retrieves all the activities",
            operationId = "getActivities",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ActivityDetail.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<ActivityDetail> getActivities(@PathVariable(name = "id") UUID activityId);

    @Operation(
            method = "POST",
            tags = Tags.ACTIVITIES,
            summary = "Creates an activity",
            operationId = "create",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreateActivity.class)
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ActivityDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostExchange(accept = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ActivityDetail create(@RequestBody CreateActivity createActivity);
}
