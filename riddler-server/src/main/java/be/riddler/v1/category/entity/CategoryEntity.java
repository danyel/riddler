package be.riddler.v1.category.entity;

import be.riddler.v1.common.entity.BaseEntity;
import be.riddler.v1.common.entity.HasValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CategoryEntity
 *
 * @author dnoulet
 * @version 1.0.0 16/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity implements HasValue<String> {
    @Column(unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<KeywordEntity> keywords = new ArrayList<>();

    @Override
    public boolean isSame(String name) {
        return Objects.equals(name, this.name);
    }
}
