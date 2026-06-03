package be.riddler.v1.menu.repository;

import be.riddler.common.repository.AbstractRepositoryIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * MenuRepositoryTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@DisplayName("Menu Repository")
class MenuRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private MenuRepository menuRepository;

    @Test
    void givenDataInTable_whenFindAll_then2ItemsReturned() {
        var menus = menuRepository.findAll();
        assertEquals(4, menus.size());
        var menuOne = menus.getFirst();
        var menuTwo = menus.get(1);
        assertEquals(UUID.fromString("bfc25c06-5a0a-42e3-9828-ae2ec16570b9"), menuOne.getId());
        assertEquals("vaadin:dashboard", menuOne.getIcon());
        assertEquals("/#/questions", menuOne.getPath());
        assertEquals("Questions", menuOne.getLabel());
        assertEquals(0, menuOne.getOrder());
        assertEquals(UUID.fromString("095a7040-3fec-4675-924d-2515f777305c"), menuTwo.getId());
        assertEquals("vaadin:user-clock", menuTwo.getIcon());
        assertEquals("/#/participants", menuTwo.getPath());
        assertEquals("Participants", menuTwo.getLabel());
        assertEquals(1, menuTwo.getOrder());
    }
}