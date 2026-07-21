package be.riddler.v1.menu.api;

import be.riddler.common.path.Paths;
import be.riddler.v1.menu.client.MenuClient;
import be.riddler.v1.menu.client.model.Menu;
import be.riddler.v1.menu.feature.RetrieveMenuForUsernameFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * MenuResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@RequiredArgsConstructor(access = PACKAGE)
@HttpExchange(Paths.MENU)
class MenuResource implements MenuClient {
    private final RetrieveMenuForUsernameFeature retrieveMenuForUsernameFeature;

    @Override
    public List<Menu> menu() {
        return retrieveMenuForUsernameFeature.retrieveMenu();
    }
}
