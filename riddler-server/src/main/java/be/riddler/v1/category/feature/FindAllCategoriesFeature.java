package be.riddler.v1.category.feature;

import be.riddler.v1.category.client.model.Category;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * FindAllCategoriesFeature
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
public interface FindAllCategoriesFeature {
    @NonNull List<@NonNull Category> findAll();
}
