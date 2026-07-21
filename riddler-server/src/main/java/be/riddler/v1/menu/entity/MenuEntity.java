package be.riddler.v1.menu.entity;

import be.riddler.v1.common.entity.BaseEntity;
import be.riddler.v1.settings.model.BookmarkType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Menu
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@Table(name = "menus")
public class MenuEntity extends BaseEntity {
    private String path;
    private String label;
    private String icon;
    @Enumerated(EnumType.STRING)
    private BookmarkType bookmarkType;
    @Column(name = "order_index")
    private Integer order;
}
