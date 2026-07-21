package be.riddler.v1.category.api;

import be.riddler.common.path.Paths;
import be.riddler.v1.category.client.CategoryClient;
import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.CreateCategory;
import be.riddler.v1.category.client.model.UpdateCategory;
import be.riddler.v1.category.feature.CreateCategoryFeature;
import be.riddler.v1.category.feature.DeleteCategoryFeature;
import be.riddler.v1.category.feature.FindAllCategoriesFeature;
import be.riddler.v1.category.feature.UpdateCategoryFeature;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * CategoryResource
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@RestController
@HttpExchange(url = Paths.CATEGORY)
@RequiredArgsConstructor(access = PACKAGE)
class CategoryResource implements CategoryClient {
    private final DeleteCategoryFeature deleteCategoryFeature;
    private final CreateCategoryFeature createCategoryFeature;
    private final FindAllCategoriesFeature findAllCategoriesFeature;
    private final UpdateCategoryFeature updateCategoryFeature;

    @Override
    public @NonNull List<@NonNull Category> findAll() {
        return findAllCategoriesFeature.findAll();
    }

    @Override
    public @NonNull Category create(@NonNull CreateCategory createCategory) {
        return createCategoryFeature.create(createCategory);
    }

    @Override
    public void delete(@NonNull UUID categoryId) {
        deleteCategoryFeature.delete(categoryId);
    }

    @Override
    public @NonNull Category update(@NonNull UUID categoryId, @NonNull UpdateCategory updateCategory) {
        return updateCategoryFeature.update(categoryId, updateCategory);
    }
}
