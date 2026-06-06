package be.riddler.v1.category.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.category.entity.CategoryEntity;
import be.riddler.v1.category.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * FindAllCategoriesFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Find All Categories Feature")
class FindAllCategoriesFeatureTest extends AbstractMockTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private FindAllCategoriesFeatureImpl ut;

    @DisplayName("When fetching categories a list of 2 items will be returned")
    @Test
    void findAll() {
        when(categoryRepository.findAll()).thenReturn(of(CategoryEntity.builder().build(), CategoryEntity.builder().build()));

        var categories = ut.findAll();

        assertNotNull(categories);
        Assertions.assertThat(categories).hasSize(2);
    }
}