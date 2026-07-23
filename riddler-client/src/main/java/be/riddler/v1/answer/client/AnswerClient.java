package be.riddler.v1.answer.client;

import be.riddler.common.header.Headers;
import be.riddler.common.tag.Tags;
import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.client.model.UpdateAnswer;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.UUID;

/**
 * AnswerApi
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Tag(name = Tags.ANSWERS)
public interface AnswerClient {

    @Operation(
            method = "GET",
            tags = Tags.ANSWERS,
            summary = "Retrieves the question by id",
            operationId = "findByQuestion",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Answer.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/question/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<Answer> findByQuestionId(@PathVariable(name = "id") UUID questionId);

    @Operation(
            method = "GET",
            tags = Tags.ANSWERS,
            summary = "Retrieves the answers by invitation id",
            operationId = "findByInvitationId",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Answer.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "/invitation/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<Answer> findByInvitationId(@PathVariable(name = "id") UUID invitationId);

    @Operation(
            method = "POST",
            tags = Tags.ANSWERS,
            summary = "Creates an answer",
            operationId = "create",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(schema = @Schema(implementation = CreateAnswer.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Answer.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostExchange
    @ResponseStatus(HttpStatus.CREATED)
    Answer create(@RequestBody CreateAnswer createAnswer);

    @Operation(
            method = "PUT",
            tags = Tags.ANSWERS,
            summary = "Updates an answer by answer id",
            operationId = "update",
            parameters = {
                    @Parameter(name = Headers.X_CORRELATION_ID, in = ParameterIn.HEADER, schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(schema = @Schema(implementation = UpdateAnswer.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Answer.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PutExchange(url = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Answer update(@PathVariable(name = "id") UUID answerId, @RequestBody UpdateAnswer updateAnswer);
}
