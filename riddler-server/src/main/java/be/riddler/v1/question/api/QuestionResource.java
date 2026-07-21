package be.riddler.v1.question.api;

import be.riddler.v1.question.client.QuestionClient;
import be.riddler.v1.question.client.model.AddOption;
import be.riddler.v1.question.client.model.CreateQuestion;
import be.riddler.v1.question.client.model.Option;
import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.client.model.QuestionId;
import be.riddler.v1.question.client.model.UpdateOption;
import be.riddler.v1.question.client.model.UpdateQuestion;
import be.riddler.v1.question.client.model.UpdateWithId;
import be.riddler.v1.question.feature.AddOptionFeature;
import be.riddler.v1.question.feature.CreateQuestionFeature;
import be.riddler.v1.question.feature.DeleteQuestionByIdFeature;
import be.riddler.v1.question.feature.GetQuestionByIdFeature;
import be.riddler.v1.question.feature.GetQuestionsByIdsFeature;
import be.riddler.v1.question.feature.GetQuestionsFeature;
import be.riddler.v1.question.feature.UpdateOptionFeature;
import be.riddler.v1.question.feature.UpdateQuestionFeature;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.UUID;

import static be.riddler.common.path.Paths.QUESTION;
import static lombok.AccessLevel.PACKAGE;

/**
 * QuestionResource
 *
 * @author dnoulet
 * @version 1.0.0 25/04/2026
 */
@RestController
@RequiredArgsConstructor(access = PACKAGE)
@HttpExchange(url = QUESTION)
class QuestionResource implements QuestionClient {
    private final CreateQuestionFeature createQuestionFeature;
    private final GetQuestionsFeature questionsFeature;
    private final DeleteQuestionByIdFeature deleteQuestionByIdFeature;
    private final GetQuestionByIdFeature getQuestionByIdFeature;
    private final UpdateQuestionFeature updateQuestionFeature;
    private final GetQuestionsByIdsFeature getQuestionsByIdsFeature;
    private final AddOptionFeature addOptionFeature;
    private final UpdateOptionFeature updateOptionFeature;

    @Override
    public Question findById(UUID uuid) {
        return getQuestionByIdFeature.byQuestionId(QuestionId.fromQuestionId(uuid));
    }

    @Override
    public Question create(CreateQuestion createQuestion) {
        return createQuestionFeature.executeWithReturn(createQuestion);
    }

    @Override
    public void delete(UUID id) {
        deleteQuestionByIdFeature.execute(QuestionId.fromQuestionId(id));
    }

    @Override
    public Question update(UUID id, UpdateQuestion updateQuestion) {
        return updateQuestionFeature.executeWithReturn(new UpdateWithId(QuestionId.fromQuestionId(id), updateQuestion));
    }

    @Override
    public @NonNull Option addOption(@NonNull AddOption addOption) {
        return addOptionFeature.addOption(addOption);
    }

    @Override
    public @NonNull Option updateOption(@NonNull UpdateOption updateOption) {
        return updateOptionFeature.updateOption(updateOption);
    }

    @Override
    public List<Question> getQuestionsById(List<UUID> questionIds) {
        if (questionIds == null || questionIds.isEmpty()) {
            return questionsFeature.findAll();
        }
        return getQuestionsByIdsFeature.findAll(questionIds);
    }
}
