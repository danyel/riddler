package be.riddler.v1.ui.api;

import be.riddler.common.path.Paths;
import be.riddler.v1.question.client.model.QuestionType;
import be.riddler.v1.ui.client.UiClient;
import be.riddler.v1.ui.client.model.SupportedLanguages;
import be.riddler.v1.ui.client.model.Translation;
import be.riddler.v1.ui.client.model.TranslationProperty;
import be.riddler.v1.ui.feature.GetIconsFeature;
import be.riddler.v1.ui.feature.GetTranslationFeature;
import be.riddler.v1.ui.feature.GetTranslationsByLanguageFeature;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.stream.Stream;

import static lombok.AccessLevel.PACKAGE;

/**
 * ComponentsResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@HttpExchange(url = Paths.UI)
@RequiredArgsConstructor(access = PACKAGE)
@Slf4j
public class UiResource implements UiClient {
    private final GetIconsFeature getIconsFeature;
    private final GetTranslationsByLanguageFeature getTranslationsByLanguageFeature;
    private final GetTranslationFeature getTranslationFeature;

    @RolesAllowed({"ADMIN", "USER", "ROLE_USER"})
    @Override
    public List<String> icons() {
        return getIconsFeature.findAll();
    }

    @Override
    public List<String> supportedLanguages() {
        return SupportedLanguages.languages();
    }

    @Override
    public List<String> questionTypes() {
        return Stream.of(QuestionType.values()).map(Enum::name).toList();
    }

    @Override
    public List<Translation> translations(String language) {
        return getTranslationsByLanguageFeature.findAll(language);
    }

    @Override
    public Translation translate(String language, String key) {
        return getTranslationFeature.translateForProperties(new TranslationProperty(language, key));
    }
}
