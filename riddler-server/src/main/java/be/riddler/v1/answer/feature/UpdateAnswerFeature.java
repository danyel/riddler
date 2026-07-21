package be.riddler.v1.answer.feature;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.UpdateAnswer;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * UpdateAnswerFeature
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
public interface UpdateAnswerFeature {
    @Transactional
    @NonNull Answer update(@NonNull UUID answerId, @NonNull UpdateAnswer updateAnswer);
}
