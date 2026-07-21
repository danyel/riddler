package be.riddler.v1.question.mapper;

import be.riddler.v1.question.client.model.AddOption;
import be.riddler.v1.question.client.model.Option;
import be.riddler.v1.question.entity.OptionEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;

/**
 * OptionMapper
 *
 * @author dnoulet
 * @version 1.0.0 29/05/2026
 */
@UtilityClass
public final class OptionMapper {
    public static @NonNull OptionEntity fromAddOption(@NonNull AddOption addOption) {
        return OptionEntity.builder()
                .value(addOption.value())
                .build();
    }

    public static @NonNull Option fromEntity(@NonNull OptionEntity optionEntity) {
        return new Option(optionEntity.getId(), optionEntity.getValue());
    }

}
