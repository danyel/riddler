package be.riddler.v1.ui.api;

import be.riddler.common.resource.AbstractResourceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ComponentsResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@DisplayName("UI Resource")
class UiResourceIntegrationTest extends AbstractResourceIntegrationTest {

    @Test
    void icons() {
        doExchange(getWebTestClient().get()
                .uri("/v1/ui/icons"))
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0]")
                .isEqualTo("vaadin:abacus");
    }

    @Test
    void name() {
        System.out.println(authenticationContent());
    }
}