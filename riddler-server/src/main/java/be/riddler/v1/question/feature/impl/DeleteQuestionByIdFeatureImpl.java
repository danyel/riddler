package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.client.model.QuestionId;
import be.riddler.v1.question.feature.DeleteQuestionByIdFeature;
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
class DeleteQuestionByIdFeatureImpl implements DeleteQuestionByIdFeature {
    private final QuestionRepository questionRepository;

    @Transactional
    @Override
    public void execute(@NonNull QuestionId questionId) {
        if (!questionRepository.existsById(questionId.id())) {
            throw new EntityNotFoundException(questionId.id().toString());
        }
        questionRepository.deleteById(questionId.id());
    }
}
