package be.riddler.v1.category.bff;

import be.riddler.v1.category.client.CategoryClient;
import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.CreateCategory;
import be.riddler.v1.category.client.model.UpdateCategory;
import be.riddler.v1.common.security.Roles;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;


/**
 * CategoryEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@BrowserCallable
@RolesAllowed({Roles.ADMIN, Roles.USER})
@RequiredArgsConstructor(access = PACKAGE)
public class CategoryEndpoint {
    private final CategoryClient categoryClient;

    public @NonNull List<@NonNull Category> findAll() {
        return categoryClient.findAll();
    }

    public @NonNull Category create(@NonNull CreateCategory createCategory) {
        return categoryClient.create(createCategory);
    }

    public void delete(@NonNull UUID categoryId) {
        categoryClient.delete(categoryId);
    }

    public @NonNull Category update(@NonNull UUID categoryId, @NonNull UpdateCategory updateCategory) {
        return categoryClient.update(categoryId, updateCategory);
    }
}
