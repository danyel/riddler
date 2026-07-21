package be.riddler.v1.answer.mapper;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.entity.AnswerEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;

/**
 * AnswerMapper
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@UtilityClass
public final class AnswerMapper {
    public static @NonNull AnswerEntity fromCreateAnswer(@NonNull CreateAnswer createAnswer) {
        return AnswerEntity.builder()
                .questionId(createAnswer.questionId())
                .invitationId(createAnswer.invitationId())
                .build();
    }

    public static @NonNull Answer fromAnswerEntity(@NonNull AnswerEntity answerEntity) {
        var solutions = answerEntity.getSolutions()
                .stream()
                .map(SolutionMapper::fromEntityToSolution)
                .toList();
        return new Answer(answerEntity.getId(), answerEntity.getInvitationId(), solutions, answerEntity.getQuestionId());
    }
}
