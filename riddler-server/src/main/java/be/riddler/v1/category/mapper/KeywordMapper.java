package be.riddler.v1.category.mapper;

import be.riddler.v1.category.client.model.CreateKeyword;
import be.riddler.v1.category.client.model.Keyword;
import be.riddler.v1.category.client.model.UpdateKeyword;
import be.riddler.v1.category.entity.KeywordEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;

/**
 * CategoryMapper
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@UtilityClass
public class KeywordMapper {
    public static @NonNull KeywordEntity fromCreateKeyword(@NonNull CreateKeyword createKeyword) {
        return KeywordEntity.builder().word(createKeyword.word()).build();
    }

    public static @NonNull KeywordEntity fromUpdateKeyword(@NonNull UpdateKeyword updateKeyword) {
        return KeywordEntity.builder().word(updateKeyword.word()).build();
    }

    public static @NonNull Keyword fromKeywordEntity(@NonNull KeywordEntity keywordEntity) {
        return new Keyword(keywordEntity.getId(), keywordEntity.getWord());
    }
}
