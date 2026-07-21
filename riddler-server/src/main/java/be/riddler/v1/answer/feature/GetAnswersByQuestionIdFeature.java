package be.riddler.v1.answer.feature;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.QuestionId;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * GetAnswersByQuestionIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
public interface GetAnswersByQuestionIdFeature {
    @Transactional
    @NonNull List<@NonNull Answer> byQuestionId(@NonNull QuestionId questionId);
}
