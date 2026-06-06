package be.riddler.v1.ui.feature;

import be.riddler.v1.ui.client.model.Translation;
import be.riddler.v1.ui.client.model.TranslationProperty;
import be.riddler.v1.ui.repository.KeyRepository;
import be.riddler.v1.ui.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PACKAGE;

/**
 * GetTranslationFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
public class GetTranslationFeature {
    private final TranslationRepository translationRepository;
    private final KeyRepository keyRepository;

    public @NonNull Translation translateForProperties(@NonNull TranslationProperty translationProperty) {
        var keyEntity = keyRepository.findByKey(translationProperty.key());
        return translationRepository.findByLanguageAndKey_Id(translationProperty.lang(), keyEntity.getId())
                .map(e -> new Translation(e.getKey().getKey(), e.getValue()))
                .orElse(new Translation(translationProperty.key(), "UNKNOWN"));
    }
}
