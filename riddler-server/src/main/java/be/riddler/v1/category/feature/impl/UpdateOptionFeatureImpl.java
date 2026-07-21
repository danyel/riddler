package be.riddler.v1.category.feature.impl;

import be.riddler.v1.question.client.model.Option;
import be.riddler.v1.question.client.model.UpdateOption;
import be.riddler.v1.question.feature.UpdateOptionFeature;
import be.riddler.v1.question.mapper.OptionMapper;
import be.riddler.v1.question.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * UpdateOptionFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 29/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class UpdateOptionFeatureImpl implements UpdateOptionFeature {
    private final OptionRepository optionRepository;

    @Transactional
    @Override
    public @NonNull Option updateOption(@NonNull UpdateOption updateOption) {
        var optionEntity = optionRepository.findById(updateOption.optionId());
        if (optionEntity.isPresent()) {
            var option = optionEntity.get();
            option.setValue(updateOption.value());
            optionRepository.save(option);
            return OptionMapper.fromEntity(option);
        }
        throw new IllegalStateException("Option with id " + updateOption.optionId() + " not found");
    }
}
