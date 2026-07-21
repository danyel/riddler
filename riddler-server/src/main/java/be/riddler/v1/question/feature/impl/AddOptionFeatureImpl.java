package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.client.model.AddOption;
import be.riddler.v1.question.client.model.Option;
import be.riddler.v1.question.entity.OptionEntity;
import be.riddler.v1.question.feature.AddOptionFeature;
import be.riddler.v1.question.mapper.OptionMapper;
import be.riddler.v1.question.repository.OptionRepository;
import be.riddler.v1.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AddOptionFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 26/05/2026
 */
@Service
@RequiredArgsConstructor
class AddOptionFeatureImpl implements AddOptionFeature {
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    @Transactional
    @Override
    public Option addOption(@NonNull AddOption addOption) {
        var questionEntity = questionRepository.findById(addOption.questionId());
        if (questionEntity.isPresent()) {
            var question = questionEntity.get();
            var optionEntity = OptionEntity.builder()
                    .value(addOption.value())
                    .question(question)
                    .build();
            optionRepository.save(optionEntity);
            question.getOptions().add(optionEntity);
            return OptionMapper.fromEntity(optionEntity);
        }
        throw new IllegalStateException("Question with id " + addOption.questionId() + " not found");
    }
}
