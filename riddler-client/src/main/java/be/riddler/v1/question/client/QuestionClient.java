package be.riddler.v1.question.client;

import be.riddler.common.tag.Tags;
import be.riddler.v1.question.client.model.AddOption;
import be.riddler.v1.question.client.model.CreateQuestion;
import be.riddler.v1.question.client.model.Option;
import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.client.model.UpdateOption;
import be.riddler.v1.question.client.model.UpdateQuestion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.UUID;

/**
 * QuestionApi
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */

@Tag(name = Tags.QUESTIONS)
public interface QuestionClient {
    @Operation(
            method = "GET",
            tags = Tags.QUESTIONS,
            summary = "Retrieves all the questions by ids",
            operationId = "getQuestionsById",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Question.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange
    @ResponseStatus(HttpStatus.OK)
    List<Question> getQuestionsById(@Parameter(description = "Optional list of question UUIDs to filter by")
                                    @RequestParam(name = "ids", required = false) @Valid @Size(min = 1) @Nullable List<@NonNull UUID> questionIds);


    @Operation(
            method = "GET",
            tags = Tags.QUESTIONS,
            summary = "Retrieve the questions corresponding to the id",
            operationId = "findById",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Question.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange(url = "{id}")
    @ResponseStatus(HttpStatus.OK)
    Question findById(@PathVariable(name = "id") UUID questionId);

    @Operation(
            method = "POST",
            tags = Tags.QUESTIONS,
            summary = "Creates a new question",
            operationId = "create",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreateQuestion.class)
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Question.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostExchange
    @ResponseStatus(HttpStatus.CREATED)
    Question create(@RequestBody CreateQuestion createQuestion);

    @Operation(
            method = "PUT",
            tags = Tags.QUESTIONS,
            summary = "Updates a question for a given id",
            operationId = "update",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", schema = @Schema(implementation = UUID.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UpdateQuestion.class)
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Question.class))
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
    @PutExchange(url = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Question update(@PathVariable(name = "id") UUID questionId, @RequestBody UpdateQuestion updateQuestion);

    @Operation(
            method = "POST",
            tags = "options",
            summary = "Adds an option to a question",
            operationId = "addOption",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AddOption.class)
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Option.class))
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
    @PostExchange(url = "/options")
    @ResponseStatus(HttpStatus.OK)
    @NonNull Option addOption(@RequestBody @NonNull AddOption addOption);

    @Operation(
            method = "PUT",
            tags = "options",
            summary = "Updates an option of a question",
            operationId = "updateOption",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UpdateOption.class)
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Option.class))
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
    @PutExchange(url = "/options")
    @ResponseStatus(HttpStatus.OK)
    @NonNull Option updateOption(@RequestBody @NonNull UpdateOption updateOption);

    @Operation(
            method = "DELETE",
            tags = Tags.QUESTIONS,
            summary = "Deletes the question for a given id",
            operationId = "delete",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Question.class))
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
    @DeleteExchange(url = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable(name = "id") UUID questionId);
}
