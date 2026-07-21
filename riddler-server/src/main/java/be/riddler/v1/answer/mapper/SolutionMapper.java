package be.riddler.v1.answer.mapper;

import be.riddler.v1.answer.client.model.CreateSolution;
import be.riddler.v1.answer.client.model.Solution;
import be.riddler.v1.answer.entity.AnswerEntity;
import be.riddler.v1.answer.entity.SolutionEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;

/**
 * SolutionMapper
 *
 * @author dnoulet
 * @version 1.0.0 25/05/2026
 */
@UtilityClass
public final class SolutionMapper {
    public static @NonNull SolutionEntity fromCreateSolutionToEntity(@NonNull CreateSolution createSolution, @NonNull AnswerEntity answerEntity) {
        return SolutionEntity.builder()
                .value(createSolution.value())
                .answer(answerEntity)
                .build();
    }

    public static @NonNull Solution fromEntityToSolution(@NonNull SolutionEntity solutionEntity) {
        return new Solution(
                solutionEntity.getId(),
                solutionEntity.getValue()
        );
    }
}
