package be.riddler.v1.category.feature.impl;

import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.feature.FindAllCategoriesFeature;
import be.riddler.v1.category.mapper.CategoryMapper;
import be.riddler.v1.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * FindAllCategoriesFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class FindAllCategoriesFeatureImpl implements FindAllCategoriesFeature {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public @NonNull List<@NonNull Category> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::fromCategoryEntity)
                .toList();
    }
}
