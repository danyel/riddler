package be.riddler.v1.category.feature.impl;

import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.CreateCategory;
import be.riddler.v1.category.client.model.CreateKeyword;
import be.riddler.v1.category.client.model.Keyword;
import be.riddler.v1.category.entity.CategoryEntity;
import be.riddler.v1.category.entity.KeywordEntity;
import be.riddler.v1.category.mapper.CategoryMapper;
import be.riddler.v1.category.mapper.KeywordMapper;
import be.riddler.v1.category.repository.CategoryRepository;
import be.riddler.v1.category.repository.KeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static be.riddler.v1.category.mapper.CategoryMapper.fromCategoryEntity;
import static be.riddler.v1.category.mapper.CategoryMapper.fromCreateCategory;
import static be.riddler.v1.category.mapper.KeywordMapper.fromCreateKeyword;
import static be.riddler.v1.fixture.Fixture.Category.CATEGORY_ID;
import static be.riddler.v1.fixture.Fixture.Keyword.KEYWORD_ID;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mockStatic;

/**
 * CreateCategoryFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Create Category Feature")
@ExtendWith(MockitoExtension.class)
class CreateCategoryFeatureTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private KeywordRepository keywordRepository;
    @InjectMocks
    private CreateCategoryFeatureImpl ut;

    @DisplayName("Given a valid request when creating the category then category and keywords will be saved and returned.")
    @Test
    void create() {
        try (MockedStatic<CategoryMapper> categoryMapper = mockStatic(CategoryMapper.class)) {
            try (MockedStatic<KeywordMapper> keywordMapper = mockStatic(KeywordMapper.class)) {
                var createKeyword = new CreateKeyword("value");
                var createCategory = new CreateCategory("name", of(createKeyword));
                var categoryEntity = new CategoryEntity();
                var keywordEntity = new KeywordEntity();

                categoryEntity.setId(CATEGORY_ID);
                categoryEntity.setKeywords(new ArrayList<>());
                keywordEntity.setWord("value");
                keywordEntity.setId(KEYWORD_ID);
                keywordEntity.setCategory(categoryEntity);
                categoryMapper.when(() -> fromCreateCategory(eq(createCategory)))
                        .thenReturn(categoryEntity);
                keywordMapper.when(() -> fromCreateKeyword(eq(createKeyword)))
                        .thenReturn(keywordEntity);
                categoryMapper.when(() -> fromCategoryEntity(eq(categoryEntity)))
                        .thenReturn(new Category(categoryEntity.getId(), categoryEntity.getName(), of(new Keyword(keywordEntity.getId(), keywordEntity.getWord()))));

                var result = ut.create(createCategory);

                var inOrder = inOrder(categoryRepository, keywordRepository);
                inOrder.verify(categoryRepository).save(categoryEntity);
                inOrder.verify(keywordRepository).save(keywordEntity);
                inOrder.verify(categoryRepository).save(categoryEntity);
                inOrder.verifyNoMoreInteractions();
                assertNotNull(result);
            }
        }
    }
}