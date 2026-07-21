package be.riddler.v1.settings.entity;

import be.riddler.v1.common.entity.IdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * SettingsEntity
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "settings")
public class SettingsEntity extends IdEntity {
    @OneToMany(targetEntity = BookmarkEntity.class, mappedBy = "settings", orphanRemoval = true)
    @Builder.Default
    private List<BookmarkEntity> bookmarks = new ArrayList<>();
    private String username;

    public @NonNull List<@NonNull BookmarkEntity> getBookmarks() {
        if (bookmarks == null) {
            bookmarks = new ArrayList<>();
        }
        return bookmarks;
    }
}
