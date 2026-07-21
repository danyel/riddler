package be.riddler.v1.question.entity;

import be.riddler.v1.common.entity.IdEntity;
import be.riddler.v1.question.client.model.QuestionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * QuestionEntity
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
@Setter
@Entity
@Table(name = "questions")
public class QuestionEntity extends IdEntity {
    private String title;
    private String question;
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OptionEntity> options = new ArrayList<>();
}
