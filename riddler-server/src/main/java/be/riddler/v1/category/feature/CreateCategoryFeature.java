package be.riddler.v1.category.feature;

import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.CreateCategory;
import org.jspecify.annotations.NonNull;

/**
 * CreateCategoryFeature
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
public interface CreateCategoryFeature {
    @NonNull Category create(@NonNull CreateCategory createCategory);
}
