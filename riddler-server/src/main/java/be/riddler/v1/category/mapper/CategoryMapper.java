package be.riddler.v1.category.mapper;

import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.CreateCategory;
import be.riddler.v1.category.entity.CategoryEntity;
import be.riddler.v1.category.entity.KeywordEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * CategoryMapper
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@UtilityClass
public class CategoryMapper {
    public static @NonNull CategoryEntity fromCreateCategory(@NonNull CreateCategory createCategory) {
        return CategoryEntity.builder().name(createCategory.name()).build();
    }

    public static @NonNull Category fromCategoryEntity(@NonNull CategoryEntity categoryEntity) {
        return new Category(categoryEntity.getId(), categoryEntity.getName(), Objects.requireNonNullElse(categoryEntity.getKeywords(), List.<KeywordEntity>of())
                .stream()
                .map(KeywordMapper::fromKeywordEntity)
                .toList());
    }
}
