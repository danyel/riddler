package be.riddler.v1.ui.feature;

import be.riddler.v1.ui.client.model.Translation;
import be.riddler.v1.ui.entity.KeyEntity;
import be.riddler.v1.ui.repository.KeyRepository;
import be.riddler.v1.ui.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * GetTranslationFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
public class GetTranslationsByLanguageFeature {
    private final TranslationRepository translationRepository;
    private final KeyRepository keyRepository;

    @Transactional
    public @NonNull List<@NonNull Translation> findAll(@NonNull String language) {
        var keys = keyRepository.findAll();
        return translationRepository.findAllByLanguageAndKey_IdIn(language, keys.stream().map(KeyEntity::getId).toList())
                .stream()
                .map(e -> new Translation(e.getKey().getKey(), e.getValue()))
                .toList();
    }
}
