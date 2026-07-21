package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.feature.GetQuestionsByIdsFeature;
import be.riddler.v1.question.mapper.QuestionMapper;
import be.riddler.v1.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class GetQuestionsByIdsFeatureImpl implements GetQuestionsByIdsFeature {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull List<@NonNull Question> findAll(List<UUID> questionIds) {
        return questionRepository.findAllById(questionIds)
                .stream()
                .map(QuestionMapper::fromQuestionEntity)
                .toList();
    }
}
