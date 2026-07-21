package be.riddler.v1.question.mapper;

import be.riddler.v1.common.entity.BaseEntity;
import be.riddler.v1.question.client.model.CreateQuestion;
import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.entity.OptionEntity;
import be.riddler.v1.question.entity.QuestionEntity;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * QuestionMapper
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@UtilityClass
public class QuestionMapper {
    public static Question fromCreateQuestion(CreateQuestion createQuestion) {
        return new Question(createQuestion.title(), createQuestion.question(), createQuestion.type());
    }

    public static QuestionEntity fromQuestion(Question question) {
        return QuestionEntity.builder().title(question.title()).question(question.question()).type(question.type()).build();
    }

    public static Question fromQuestionEntity(QuestionEntity questionEntity) {
        return new Question(questionEntity.getId(),
                questionEntity.getTitle(),
                questionEntity.getQuestion(),
                questionEntity.getType(),
                Objects.requireNonNullElse(questionEntity.getOptions(), List.<OptionEntity>of())
                        .stream()
                        .sorted(Comparator.comparing(BaseEntity::getCreatedAt))
                        .map(OptionMapper::fromEntity)
                        .toList());
    }
}
