package be.riddler.v1.category.entity;


import be.riddler.v1.common.entity.HasValue;
import be.riddler.v1.common.entity.IdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
 * KeywordEntity
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "category_keywords")
public class KeywordEntity extends IdEntity implements HasValue<String> {

    @Column(nullable = false)
    private String word;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Override
    public boolean isSame(String value) {
        return Objects.equals(word, value);
    }
}