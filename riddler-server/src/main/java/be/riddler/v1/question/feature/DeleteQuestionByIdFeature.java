package be.riddler.v1.question.feature;

import be.riddler.v1.question.client.model.QuestionId;
import org.jspecify.annotations.NonNull;

/**
 * DeleteQuestionByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface DeleteQuestionByIdFeature {
    void execute(@NonNull QuestionId questionId);
}
