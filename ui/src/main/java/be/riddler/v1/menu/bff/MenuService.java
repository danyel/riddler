package be.riddler.v1.menu.bff;

import be.riddler.v1.menu.client.MenuClient;
import be.riddler.v1.menu.client.model.Menu;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * MenuService
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@BrowserCallable
@PermitAll
@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
public class MenuService {
    private final MenuClient menuClient;

    public @NonNull List<@NonNull Menu> menu() {
        return menuClient.menu();
    }
}
