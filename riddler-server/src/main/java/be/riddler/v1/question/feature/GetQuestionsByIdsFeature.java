package be.riddler.v1.question.feature;

import be.riddler.v1.question.client.model.Question;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * GetQuestionsByIdsFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface GetQuestionsByIdsFeature {
    @NonNull List<@NonNull Question> findAll(List<UUID> questionIds);
}
