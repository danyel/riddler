package be.riddler.v1.question.feature;

import be.riddler.v1.question.client.model.CreateQuestion;
import be.riddler.v1.question.client.model.Question;
import org.jspecify.annotations.NonNull;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface CreateQuestionFeature {

    @NonNull Question executeWithReturn(@NonNull CreateQuestion createQuestion);
}
