package be.riddler.v1.category.feature.impl;

import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.Keyword;
import be.riddler.v1.category.client.model.UpdateCategory;
import be.riddler.v1.category.client.model.UpdateKeyword;
import be.riddler.v1.category.entity.CategoryEntity;
import be.riddler.v1.category.entity.KeywordEntity;
import be.riddler.v1.category.mapper.CategoryMapper;
import be.riddler.v1.category.repository.CategoryRepository;
import be.riddler.v1.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static be.riddler.v1.category.mapper.CategoryMapper.fromCategoryEntity;
import static be.riddler.v1.fixture.Fixture.Category.CATEGORY_ID;
import static be.riddler.v1.fixture.Fixture.Keyword.KEYWORD_ID;
import static be.riddler.v1.fixture.Fixture.Solution.SOLUTION_ID;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * UpdateCategoryFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Update Category Feature")
@ExtendWith(MockitoExtension.class)
class UpdateCategoryFeatureTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private UpdateCategoryFeatureImpl ut;

    @DisplayName("When updating an existing category than category will be updated")
    @Test
    void update() {
        try (MockedStatic<CategoryMapper> categoryMapper = mockStatic(CategoryMapper.class)) {
            var categoryEntity = mock(CategoryEntity.class);
            categoryMapper.when(() -> fromCategoryEntity(categoryEntity))
                    .thenReturn(new Category(CATEGORY_ID, "Other value", List.of()));
            when(categoryEntity.isSame(eq("Other value"))).thenReturn(false);
            when(categoryRepository.findById(CATEGORY_ID)).thenReturn(of(categoryEntity));

            ut.update(CATEGORY_ID, new UpdateCategory("Other value", List.of()));

            var inOrder = inOrder(categoryRepository, categoryEntity);
            inOrder.verify(categoryRepository).findById(CATEGORY_ID);
            inOrder.verify(categoryEntity).setName("Other value");
            inOrder.verify(categoryRepository).save(categoryEntity);
            inOrder.verifyNoMoreInteractions();
        }
    }


    @DisplayName("When updating an existing category than category will be updated")
    @Test
    void updateWithSolutions() {
        try (MockedStatic<CategoryMapper> categoryMapper = mockStatic(CategoryMapper.class)) {
            var categoryEntity = Fixture.Category.CATEGORY_ENTITY;
            categoryEntity.setKeywords(new ArrayList<>(List.of(KeywordEntity.builder()
                            .category(categoryEntity)
                            .word("word")
                            .id(KEYWORD_ID)
                            .build(),
                    KeywordEntity.builder()
                            .category(categoryEntity)
                            .word("word")
                            .id(SOLUTION_ID)
                            .build())));
            categoryMapper.when(() -> fromCategoryEntity(categoryEntity))
                    .thenReturn(new Category(CATEGORY_ID, "Test Category", List.of(new Keyword(KEYWORD_ID, "value"), new Keyword(UUID.randomUUID(), "otherValue"))));
            when(categoryRepository.findById(CATEGORY_ID)).thenReturn(of(categoryEntity));

            ut.update(CATEGORY_ID, new UpdateCategory("Test Category", List.of(new UpdateKeyword(SOLUTION_ID, "word"), new UpdateKeyword(KEYWORD_ID, "value"), new UpdateKeyword(null, "value"))));

            verify(categoryRepository).findById(CATEGORY_ID);
            verify(categoryRepository).save(categoryEntity);
            verifyNoMoreInteractions(categoryRepository);
        }
    }

    @DisplayName("When trying to update an unexisting category than category not found is thrown")
    @Test
    void notFound() {
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(empty());

        var categoryNotFound = assertThrows(CategoryNotFound.class, () -> ut.update(CATEGORY_ID, new UpdateCategory("", List.of())));

        assertEquals("Category with id '%s' not found".formatted(CATEGORY_ID), categoryNotFound.getDescription());
    }
}