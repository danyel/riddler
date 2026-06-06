package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.feature.GetQuestionsFeature;
import be.riddler.v1.question.mapper.QuestionMapper;
import be.riddler.v1.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class GetQuestionsFeatureImpl implements GetQuestionsFeature {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull List<@NonNull Question> findAll() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionMapper::fromQuestionEntity)
                .toList();
    }
}
