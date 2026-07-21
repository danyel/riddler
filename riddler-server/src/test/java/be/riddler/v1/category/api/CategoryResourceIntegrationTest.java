package be.riddler.v1.category.api;

import be.riddler.common.path.Paths;
import be.riddler.common.resource.AbstractResourceIntegrationTest;
import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.CreateCategory;
import be.riddler.v1.category.client.model.CreateKeyword;
import be.riddler.v1.category.client.model.UpdateCategory;
import be.riddler.v1.category.client.model.UpdateKeyword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static be.riddler.v1.fixture.Fixture.Category.CATEGORY_ID;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * CategoryResourceIntegrationTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Category Resource")
class CategoryResourceIntegrationTest extends AbstractResourceIntegrationTest {

    @DisplayName("1 Fetches 6 categories")
    @Test
    void findAll() {
        var response = doExchange(getWebTestClient().get().uri(Paths.CATEGORY));
        var categories = response.expectStatus()
                .isOk()
                .expectBody(Category[].class)
                .returnResult()
                .getResponseBody();

        assertThat(categories).isNotNull();
        assertThat(categories).isNotEmpty();
        assertThat(categories).hasSize(6);
    }

    @DisplayName("2 Create a new category")
    @Test
    void create() {
        var category = doExchange(getWebTestClient().post()
                .uri(Paths.CATEGORY)
                .bodyValue(new CreateCategory("name", of(new CreateKeyword("word")))))
                .expectStatus()
                .isCreated()
                .expectBody(Category.class)
                .returnResult()
                .getResponseBody();

        assertThat(category).isNotNull();
        assertThat(category.id()).isNotNull();
    }

    @DisplayName("3 Updating a category")
    @Test
    void update() {
        var category = doExchange(getWebTestClient().put()
                .uri("%s/%s".formatted(Paths.CATEGORY, CATEGORY_ID))
                .contentType(APPLICATION_JSON)
                .bodyValue(new UpdateCategory("another name", of(new UpdateKeyword(null, "word")))))
                .expectStatus()
                .isOk()
                .expectBody(Category.class)
                .returnResult()
                .getResponseBody();

        assertThat(category).isNotNull();
        assertThat(category.keywords()).isNotEmpty();
        assertThat(category.keywords().getFirst().word()).isEqualTo("word");

        category = doExchange(getWebTestClient().put()
                .uri("%s/%s".formatted(Paths.CATEGORY, CATEGORY_ID))
                .contentType(APPLICATION_JSON)
                .bodyValue(new UpdateCategory("another name", of(new UpdateKeyword(category.keywords().getFirst().id(), "word"), new UpdateKeyword(null, "a new word")))))
                .expectStatus()
                .isOk()
                .expectBody(Category.class)
                .returnResult()
                .getResponseBody();

        assertThat(category).isNotNull();
        assertThat(category.keywords()).hasSize(2);
        assertThat(category.keywords().get(1).word()).isEqualTo("a new word");
    }

    @DisplayName("4 Delete Not found")
    @Test
    void deleteNotFound() {
        doExchange(getWebTestClient().delete()
                .uri("%s/%s".formatted(Paths.CATEGORY, newId())))
                .expectStatus()
                .isNotFound();
    }

    @DisplayName("5 Delete")
    @Test
    void delete() {
        doExchange(getWebTestClient().delete()
                .uri("%s/%s".formatted(Paths.CATEGORY, CATEGORY_ID)))
                .expectStatus()
                .isNoContent();
    }
}