package be.riddler.v1.category.feature.impl;

import be.riddler.v1.category.feature.DeleteCategoryFeature;
import be.riddler.v1.category.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * DeleteCategoryFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteCategoryFeatureImpl implements DeleteCategoryFeature {
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void delete(@NonNull UUID categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new CategoryNotFound(categoryId);
        }
    }
}
