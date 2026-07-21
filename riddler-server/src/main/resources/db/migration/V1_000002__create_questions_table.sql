--liquibase formatted sql
--changeset dnoulet:V1_000001_create_questions
CREATE TABLE questions
(
    ID         UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    QUESTION   text         NOT NULL,
    TYPE       VARCHAR(100) NOT NULL,
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE
);

--changeset dnoulet:V1_000001_create_questions_data context:lcl
INSERT INTO questions (id, title, question, type)
VALUES ('6d2c7dee-740a-47f1-99b0-4e4167182ae7', 'What is the following', 'Question 1', 'SINGLE_CHOICE');
INSERT INTO questions (id, title, question, type)
VALUES ('3cee42fb-d8da-4127-8f0e-3ddfab263f34', 'What do you thing about a', 'Question 2', 'OPEN');
INSERT INTO questions (id, title, question, type)
VALUES ('a2b20b03-9d96-41d4-8e83-a35b21c21fe7', 'Chopse one or more', 'Question 3', 'MULTIPLE_CHOICE');
INSERT INTO questions (id, title, question, type)
VALUES ('98b96a1f-731f-47f2-871c-99f8cbd12126', 'Review MenuEntity', 'package be.riddler.v1.menu.entity;

import be.riddler.v1.common.entity.BaseEntity;
import be.riddler.v1.settings.model.BookmarkType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

/**
 * Menu
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "menus")
public class MenuEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String path;
    private String label;
    private String icon;
    @Enumerated(EnumType.STRING)
    private BookmarkType bookmarkType;
    @Column(name = "order_index")
    private Integer order;
}
', 'REVIEW');
INSERT INTO questions (id, title, question, type)
VALUES ('b43d40ad-5373-4e1f-a833-da079da904ec', 'Review FindAllUsernameFeature', 'package be.riddler.v1.menu.feature.impl;

import be.riddler.v1.menu.client.model.Menu;
import be.riddler.v1.menu.entity.MenuConfigurationEntity;
import be.riddler.v1.menu.feature.RetrieveMenuForUsernameFeature;
import be.riddler.v1.menu.repository.MenuConfigurationRepository;
import be.riddler.v1.menu.repository.MenuRepository;
import be.riddler.v1.settings.constants.UserContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * FindAllByUsernameFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class RetrieveMenuForUsernameFeatureImpl implements RetrieveMenuForUsernameFeature {
    private final MenuRepository menuRepository;
    private final MenuConfigurationRepository menuConfigurationRepository;

    @Override
    public @NonNull List<@NonNull Menu> retrieveMenu() {
        var menuConfigurations = menuConfigurationRepository.findAllByRoleIn(UserContext.roles().stream().map(d -> d.replace("ROLE_", "")).toList());
        return menuRepository.findAllById(menuConfigurations.stream()
                        .map(MenuConfigurationEntity::getMenuId)
                        .toList())
                .stream()
                .map(menuEntity -> new Menu(menuEntity.getPath(), menuEntity.getLabel(), menuEntity.getIcon(), menuEntity.getBookmarkType(), menuEntity.getOrder()))
                .sorted(Comparator.comparing(Menu::order))
                .toList();
    }
}
', 'REVIEW');

