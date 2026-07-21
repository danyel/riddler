package be.riddler.v1.category.feature;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * DeleteCategoryFeature
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
public interface DeleteCategoryFeature {
    void delete(@NonNull UUID categoryId);
}
