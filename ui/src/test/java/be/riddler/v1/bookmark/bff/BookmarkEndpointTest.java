package be.riddler.v1.bookmark.bff;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.settings.client.SettingsClient;
import be.riddler.v1.settings.model.BookmarkType;
import be.riddler.v1.settings.model.CreateBookmark;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.EnumSet;

import static be.riddler.v1.fixture.Fixture.Bookmark.BOOKMARK_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * BookmarkEndpointTest
 *
 * @author dnoulet
 * @version 1.0.0 06/06/2026
 */
@DisplayName("Bookmark Endpoint Test")
class BookmarkEndpointTest extends AbstractMockTest {
    @Mock
    private SettingsClient settingsClient;
    @InjectMocks
    private BookmarkEndpoint bookmarkEndpoint;


    @DisplayName("Create Bookmark")
    @Test
    void bookmark() {
        var createBookmark = mock(CreateBookmark.class);
        bookmarkEndpoint.bookmark(createBookmark);

        verify(settingsClient, times(1)).createBookmark(createBookmark);
        verifyNoMoreInteractions(settingsClient);
    }

    @DisplayName("Delete Bookmark")
    @Test
    void deleteBookmark() {
        bookmarkEndpoint.deleteBookmark(BOOKMARK_ID);
        verify(settingsClient, times(1)).deleteBookmark(eq(BOOKMARK_ID));
        verifyNoMoreInteractions(settingsClient);
    }

    @DisplayName("Bookmark Types")
    @Test
    void bookmarkTypes() {
        var bookmarkTypes = EnumSet.allOf(BookmarkType.class)
                .stream()
                .map(Enum::name)
                .toList();
        var bookmarkedTypes = bookmarkEndpoint.bookmarkTypes();
        assertEquals(bookmarkTypes, bookmarkedTypes);
        verifyNoInteractions(settingsClient);
    }
}