package be.riddler.v1.answer.feature;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * CreateAnswerFeature
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
public interface CreateAnswerFeature {
    @Transactional
    @NonNull Answer create(@NonNull CreateAnswer createAnswer);
}
