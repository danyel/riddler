package be.riddler.v1.category.mapper;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.category.client.model.CreateKeyword;
import be.riddler.v1.category.client.model.UpdateKeyword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static be.riddler.v1.fixture.Fixture.Keyword.KEYWORD_ENTITY;
import static be.riddler.v1.fixture.Fixture.Keyword.KEYWORD_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * KeywordMapperTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Keyword Mapper")
class KeywordMapperTest extends AbstractMockTest {

    @DisplayName("Map create keyword to  keyword entity")
    @Test
    void fromCreateKeyword() {
        var keywordEntity = KeywordMapper.fromCreateKeyword(new CreateKeyword("word"));

        assertEquals("word", keywordEntity.getWord());
        assertNull(keywordEntity.getCategory());
    }

    @DisplayName("Map update keyword")
    @Test
    void fromUpdateKeyword() {
        var keywordEntity = KeywordMapper.fromUpdateKeyword(new UpdateKeyword(KEYWORD_ID, "word"));
        assertEquals("word", keywordEntity.getWord());
        assertNull(keywordEntity.getCategory());
        assertNull(keywordEntity.getId());
    }

    @DisplayName("Map Keyword Entity to Keyword")
    @Test
    void fromKeywordEntity() {
        var keyword = KeywordMapper.fromKeywordEntity(KEYWORD_ENTITY);
        assertEquals(KEYWORD_ID, keyword.id());
        assertEquals("word", keyword.word());
    }
}