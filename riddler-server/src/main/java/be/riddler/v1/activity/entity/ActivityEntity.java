package be.riddler.v1.activity.entity;

import be.riddler.v1.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

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
@Table(name = "activities")
public class ActivityEntity extends BaseEntity {
    private UUID questionId;
    private String actionType;
    private String elementId;
    @Column(name = "action_time")
    private Instant timestamp;
    private String additionalData;
}
