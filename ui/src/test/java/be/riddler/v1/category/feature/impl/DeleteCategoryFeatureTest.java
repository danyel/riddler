package be.riddler.v1.category.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static be.riddler.v1.fixture.Fixture.Category.CATEGORY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * DeleteCategoryFeatureImplTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Delete Category Feature")
class DeleteCategoryFeatureTest extends AbstractMockTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private DeleteCategoryFeatureImpl ut;

    @DisplayName("Given a valid category id when deleting the entity than no error occurred")
    @Test
    void delete() {
        when(categoryRepository.existsById(CATEGORY_ID)).thenReturn(true);

        ut.delete(CATEGORY_ID);

        verify(categoryRepository).existsById(CATEGORY_ID);
        verify(categoryRepository).deleteById(CATEGORY_ID);
        verifyNoMoreInteractions(categoryRepository);
    }

    @DisplayName("Given an invalid category id when deleting then NotFound thrown")
    @Test
    void deleteThrowsException() {
        when(categoryRepository.existsById(CATEGORY_ID)).thenReturn(false);

        var notFoundException = assertThrows(CategoryNotFound.class, () -> ut.delete(CATEGORY_ID));

        verify(categoryRepository).existsById(CATEGORY_ID);
        assertEquals("Category with id '%s' not found".formatted(CATEGORY_ID), notFoundException.getDescription());
        verifyNoMoreInteractions(categoryRepository);
    }
}