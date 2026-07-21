--liquibase formatted sql
--changeset dnoulet:V1_000001_create_menus
CREATE TABLE menus
(
    ID            UUID PRIMARY KEY,
    PATH          VARCHAR(255) NOT NULL,
    LABEL         VARCHAR(100) NOT NULL,
    ICON          VARCHAR(100) NOT NULL,
    BOOKMARK_TYPE VARCHAR(30)  NOT NULL,
    ORDER_INDEX   INT       DEFAULT 0,
    created_by VARCHAR(50) NOT NULL,
    updated_by VARCHAR(50),
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE menu_configurations
(
    ID         UUID PRIMARY KEY,
    MENU_ID    UUID         NOT NULL,
    ROLE VARCHAR(100) NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_by VARCHAR(50),
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE
);

--changeset dnoulet:V1_000001_create_menus_data context:lcl
INSERT INTO menus (id, label, icon, path, bookmark_type, order_index, created_by)
VALUES ('bfc25c06-5a0a-42e3-9828-ae2ec16570b9', 'Questions', 'vaadin:dashboard', '/#/questions', 'QUESTIONS', 0,
        'admin');
INSERT INTO menus (id, label, icon, path, bookmark_type, order_index, created_by)
VALUES ('095a7040-3fec-4675-924d-2515f777305c', 'Participants', 'vaadin:user-clock', '/#/participants', 'PARTICIPANTS',
        1, 'admin');
INSERT INTO menus (id, label, icon, path, bookmark_type, order_index, created_by)
VALUES ('bc4b2e0c-27af-4428-bf41-3f942d4e4dce', 'Publications', 'vaadin:newspaper', '/#/publications',
        'PUBLICATIONS',
        1, 'admin');
INSERT INTO menus (id, label, icon, path, bookmark_type, order_index, created_by)
VALUES ('bf0adea7-a6c7-45da-bfb7-5a5d1bcc69db', 'Administration', 'vaadin:cogs', '/#/administrations',
        'ADMINISTRATIONS',
        1, 'admin');
INSERT INTO menu_configurations(id, menu_id, role, created_by)
VALUES ('4709ebc1-139a-4e9f-be70-8003f13a634f', 'bfc25c06-5a0a-42e3-9828-ae2ec16570b9', 'USER', 'admin');
INSERT INTO menu_configurations(id, menu_id, role, created_by)
VALUES ('a501a559-7276-4eaf-a033-675b0bc499a5', 'bfc25c06-5a0a-42e3-9828-ae2ec16570b9', 'ADMIN', 'admin');
INSERT INTO menu_configurations(id, menu_id, role, created_by)
VALUES ('3a7a8cb6-7e68-448d-9a0f-8b833f39290f', '095a7040-3fec-4675-924d-2515f777305c', 'USER', 'admin');
INSERT INTO menu_configurations(id, menu_id, role, created_by)
VALUES ('19c31479-d258-45f7-b970-85e5746d869a', '095a7040-3fec-4675-924d-2515f777305c', 'ADMIN', 'admin');
INSERT INTO menu_configurations(id, menu_id, role, created_by)
VALUES ('aa3e07e7-7b58-45f4-bd7a-8423a8a53440', 'bf0adea7-a6c7-45da-bfb7-5a5d1bcc69db', 'ADMIN', 'admin');
INSERT INTO menu_configurations(id, menu_id, role, created_by)
VALUES ('6050c122-682c-4388-9509-1a65b73f8cf4', 'bc4b2e0c-27af-4428-bf41-3f942d4e4dce', 'USER', 'admin');
INSERT INTO menu_configurations(id, menu_id, role, created_by)
VALUES ('78ea9842-0fbb-4578-8616-14ccf1a95019', 'bc4b2e0c-27af-4428-bf41-3f942d4e4dce', 'ADMIN', 'admin');