package be.riddler.v1.settings.feature.impl;

import be.riddler.v1.settings.constants.UserContext;
import be.riddler.v1.settings.entity.SettingsEntity;
import be.riddler.v1.settings.feature.FindSettingsByUsernameFeature;
import be.riddler.v1.settings.mapper.SettingsMapper;
import be.riddler.v1.settings.model.Settings;
import be.riddler.v1.settings.repository.SettingsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindSettingsByUsernameFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindSettingsByUsernameFeatureImpl implements FindSettingsByUsernameFeature {
    private final SettingsRepository settingsRepository;

    @Transactional
    @Override
    public @NonNull Settings findSettingsByUsername() {
        var settingsEntity = settingsRepository.findByUsername(UserContext.username());
        var roles = UserContext.roles();
        if (settingsEntity.isPresent()) {
            return settingsEntity.map(settings -> SettingsMapper.fromSettingsEntity(settings, roles)).get();
        } else {
            var newSettings = SettingsEntity.builder()
                    .username(UserContext.username())
                    .build();
            settingsRepository.save(newSettings);
            return SettingsMapper.fromSettingsEntity(newSettings, roles);
        }
    }
}
