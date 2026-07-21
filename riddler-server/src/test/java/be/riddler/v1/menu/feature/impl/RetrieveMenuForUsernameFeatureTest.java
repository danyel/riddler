package be.riddler.v1.menu.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.fixture.Fixture;
import be.riddler.v1.menu.repository.MenuConfigurationRepository;
import be.riddler.v1.menu.repository.MenuRepository;
import be.riddler.v1.settings.constants.UserContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mockStatic;

/**
 * RetrieveMenuForUsernameFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Retrieve Menu For Username Feature Test")
class RetrieveMenuForUsernameFeatureTest extends AbstractMockTest {
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private MenuConfigurationRepository menuConfigurationRepository;
    @InjectMocks
    private RetrieveMenuForUsernameFeatureImpl ut;

    @DisplayName("Given a valid user when retrieving the menu then a list of menus will be returned")
    @Test
    void retrieveMenu() {
        try (var userContext = mockStatic(UserContext.class)) {
            var roles = List.of("ADMIN", "USER");
            var mappedRoles = List.of("ROLE_ADMIN", "ROLE_USER");
            userContext.when(UserContext::roles).thenReturn(mappedRoles);
            given(menuConfigurationRepository.findAllByRoleIn(roles)).willReturn(List.of(Fixture.MenuConfiguration.MENU_CONFIGURATION_ENTITY));
            given(menuRepository.findAllById(List.of(Fixture.Menu.MENU_ID))).willReturn(List.of(Fixture.Menu.MENU_ENTITY));

            var menus = ut.retrieveMenu();

            var inOrder = inOrder(menuRepository, menuConfigurationRepository);
            inOrder.verify(menuConfigurationRepository).findAllByRoleIn(roles);
            inOrder.verify(menuRepository).findAllById(List.of(Fixture.Menu.MENU_ID));
            inOrder.verifyNoMoreInteractions();
            assertThat(menus).hasSize(1);
        }
    }
}