package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.client.model.CreateQuestion;
import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.feature.CreateQuestionFeature;
import be.riddler.v1.question.mapper.QuestionMapper;
import be.riddler.v1.question.repository.QuestionRepository;
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
class CreateQuestionFeatureImpl implements CreateQuestionFeature {
    private final QuestionRepository questionRepository;

    @Transactional
    public @NonNull Question executeWithReturn(@NonNull CreateQuestion createQuestion) {
        var question = QuestionMapper.fromCreateQuestion(createQuestion);
        var questionEntity = QuestionMapper.fromQuestion(question);
        questionRepository.save(questionEntity);
        return QuestionMapper.fromQuestionEntity(questionEntity);
    }
}
