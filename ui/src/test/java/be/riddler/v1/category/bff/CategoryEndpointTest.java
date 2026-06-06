package be.riddler.v1.category.bff;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.category.client.CategoryClient;
import be.riddler.v1.category.client.model.CreateCategory;
import be.riddler.v1.category.client.model.UpdateCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static be.riddler.v1.fixture.Fixture.Category.CATEGORY_ID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * CategoryEndpointTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Category Endpoint")
class CategoryEndpointTest extends AbstractMockTest {
    @Mock
    private CategoryClient categoryClient;
    @InjectMocks
    private CategoryEndpoint categoryEndpoint;

    @Test
    void findAll() {
        categoryEndpoint.findAll();

        verify(categoryClient).findAll();
    }

    @Test
    void create() {
        var createCategory = mock(CreateCategory.class);
        categoryEndpoint.create(createCategory);

        verify(categoryClient).create(createCategory);
    }

    @Test
    void delete() {
        categoryEndpoint.delete(CATEGORY_ID);

        verify(categoryClient).delete(CATEGORY_ID);
    }

    @Test
    void update() {
        var updateCategory = mock(UpdateCategory.class);
        categoryEndpoint.update(CATEGORY_ID, updateCategory);

        verify(categoryClient).update(CATEGORY_ID, updateCategory);
    }
}