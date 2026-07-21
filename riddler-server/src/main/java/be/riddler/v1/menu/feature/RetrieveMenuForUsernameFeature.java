package be.riddler.v1.menu.feature;

import be.riddler.v1.menu.client.model.Menu;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * RetrieveMenuForUsernameFeature
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
public interface RetrieveMenuForUsernameFeature {
    @NonNull List<@NonNull Menu> retrieveMenu();
}
