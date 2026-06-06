package be.riddler.v1.category.feature.impl;

import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.UpdateCategory;
import be.riddler.v1.category.client.model.UpdateKeyword;
import be.riddler.v1.category.entity.KeywordEntity;
import be.riddler.v1.category.feature.UpdateCategoryFeature;
import be.riddler.v1.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

import static be.riddler.v1.category.mapper.CategoryMapper.fromCategoryEntity;
import static be.riddler.v1.category.mapper.KeywordMapper.fromUpdateKeyword;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PACKAGE;

/**
 * UpdateCategoryFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class UpdateCategoryFeatureImpl implements UpdateCategoryFeature {
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public @NonNull Category update(@NonNull UUID categoryId, @NonNull UpdateCategory updateCategory) {
        var categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFound(categoryId));

        if (!categoryEntity.isSame(updateCategory.name())) {
            categoryEntity.setName(updateCategory.name());
        }

        var uuidKeywordMap = categoryEntity.getKeywords()
                .stream()
                .collect(Collectors.toMap(KeywordEntity::getId, (keywordEntity) -> keywordEntity));
        var toKeep = updateCategory.keywords().stream().map(UpdateKeyword::id).toList();
        var keywordIds = uuidKeywordMap.keySet()
                .stream()
                .filter(keywordId -> !toKeep.contains(keywordId))
                .toList();
        updateCategory.keywords()
                .forEach(updateKeyword -> {
                    if (nonNull(updateKeyword.id()) && uuidKeywordMap.containsKey(updateKeyword.id())) {
                        var keywordEntity = uuidKeywordMap.get(updateKeyword.id());
                        if (!keywordEntity.isSame(updateKeyword.word())) {
                            keywordEntity.setWord(updateKeyword.word());
                        }
                    } else {
                        var keywordToBeSaved = fromUpdateKeyword(updateKeyword);
                        keywordToBeSaved.setCategory(categoryEntity);
                        categoryEntity.getKeywords().add(keywordToBeSaved);
                    }
                });
        keywordIds.forEach(keywordId -> categoryEntity.getKeywords().remove(uuidKeywordMap.get(keywordId)));
        categoryRepository.save(categoryEntity);
        return fromCategoryEntity(categoryEntity);
    }
}
