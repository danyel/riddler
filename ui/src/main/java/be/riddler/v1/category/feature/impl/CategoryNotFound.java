package be.riddler.v1.category.feature.impl;

import be.riddler.v1.common.http.NotFound;

import java.util.UUID;

/**
 * CategoryNotFound
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
public class CategoryNotFound extends NotFound {
    public CategoryNotFound(UUID categoryId) {
        super("Category", categoryId);
    }
}
