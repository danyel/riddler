package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.client.model.UpdateWithId;
import be.riddler.v1.question.feature.UpdateQuestionFeature;
import be.riddler.v1.question.mapper.QuestionMapper;
import be.riddler.v1.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class UpdateQuestionFeatureImpl implements UpdateQuestionFeature {
    private final QuestionRepository questionRepository;

    @Transactional
    @Override
    public @NonNull Question executeWithReturn(@NonNull UpdateWithId updateWithId) {
        var questionEntity = questionRepository.findById(updateWithId.questionId().id());
        if (questionEntity.isPresent()) {
            questionEntity.ifPresent(e -> {
                e.setQuestion(updateWithId.question().question());
                e.setType(updateWithId.question().type());
                e.setTitle(updateWithId.question().title());
                questionRepository.save(e);
            });
            return QuestionMapper.fromQuestionEntity(questionEntity.get());
        } else {
            throw new EntityNotFoundException("Question with id " + updateWithId.questionId().id() + " not found");
        }
    }
}
