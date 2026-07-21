package be.riddler.v1.answer.entity;

import be.riddler.v1.common.entity.HasValue;
import be.riddler.v1.common.entity.IdEntity;
import jakarta.persistence.Entity;
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

import java.util.Objects;

/**
 * SolutionEntity
 *
 * @author dnoulet
 * @version 1.0.0 25/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "answer_solutions")
public class SolutionEntity extends IdEntity implements HasValue<String> {
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AnswerEntity answer;

    @Override
    public boolean isSame(String value) {
        return Objects.equals(this.value, value);
    }
}
