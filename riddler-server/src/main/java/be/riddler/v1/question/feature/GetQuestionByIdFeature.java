package be.riddler.v1.question.feature;

import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.client.model.QuestionId;
import org.jspecify.annotations.NonNull;

/**
 * GetQuestionByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface GetQuestionByIdFeature {
    @NonNull Question byQuestionId(@NonNull QuestionId questionId);
}
