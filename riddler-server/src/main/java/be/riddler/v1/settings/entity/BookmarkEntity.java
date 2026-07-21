package be.riddler.v1.settings.entity;

import be.riddler.v1.common.entity.IdEntity;
import be.riddler.v1.settings.model.BookmarkType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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
@Table(name = "bookmarks")
public class BookmarkEntity extends IdEntity {
    @Enumerated(EnumType.STRING)
    private BookmarkType bookmarkType;
    private String label;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settings_id")
    private SettingsEntity settings;
}
