package be.riddler.v1.answer.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Answer
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record CreateAnswer(
        @NonNull @Valid List<@NonNull CreateSolution> solutions,
        @NonNull @Valid @JsonProperty(value = "question_id") UUID questionId,
        @NonNull @Valid @JsonProperty(value = "invitation_id") UUID invitationId
) {
}
