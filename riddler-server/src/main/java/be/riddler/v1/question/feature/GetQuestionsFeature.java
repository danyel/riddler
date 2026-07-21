package be.riddler.v1.question.feature;

import be.riddler.v1.question.client.model.Question;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * GetQuestionsFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface GetQuestionsFeature {
    @NonNull List<@NonNull Question> findAll();
}
