package be.riddler.v1.category.feature;

import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.UpdateCategory;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * UpdateCategoryFeature
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
public interface UpdateCategoryFeature {
    @NonNull Category update(@NonNull UUID categoryId, @NonNull UpdateCategory updateCategory);
}
