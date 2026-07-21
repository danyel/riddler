package be.riddler.v1.category.feature.impl;

import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.CreateCategory;
import be.riddler.v1.category.entity.CategoryEntity;
import be.riddler.v1.category.entity.KeywordEntity;
import be.riddler.v1.category.feature.CreateCategoryFeature;
import be.riddler.v1.category.mapper.CategoryMapper;
import be.riddler.v1.category.mapper.KeywordMapper;
import be.riddler.v1.category.repository.CategoryRepository;
import be.riddler.v1.category.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreateCategoryFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class CreateCategoryFeatureImpl implements CreateCategoryFeature {
    private final CategoryRepository categoryRepository;
    private final KeywordRepository keywordRepository;

    @Transactional
    @Override
    public @NonNull Category create(@NonNull CreateCategory createCategory) {
        CategoryEntity categoryEntity = CategoryMapper.fromCreateCategory(createCategory);
        categoryRepository.save(categoryEntity);
        categoryEntity.setKeywords(createCategory.keywords()
                .stream()
                .map(createKeyword -> {
                    KeywordEntity keywordEntity = KeywordMapper.fromCreateKeyword(createKeyword);
                    keywordEntity.setCategory(categoryEntity);
                    return keywordRepository.save(keywordEntity);
                })
                .collect(Collectors.toList()));
        categoryRepository.save(categoryEntity);
        return CategoryMapper.fromCategoryEntity(categoryEntity);
    }
}
