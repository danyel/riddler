--liquibase formatted sql
--changeset dnoulet:V1_000009__create_settings_and_bookmarks_table
CREATE TABLE settings
(
    id       UUID         NOT NULL,
    username VARCHAR(255) NOT NULL,
    CONSTRAINT pk_settings PRIMARY KEY (id)
);

CREATE TABLE bookmarks
(
    id            UUID NOT NULL,
    bookmark_type VARCHAR(30),
    label         VARCHAR(255),
    path          VARCHAR(255),
    settings_id   UUID,
    CONSTRAINT pk_bookmarks PRIMARY KEY (id),
    CONSTRAINT fk_bookmarks_on_settings FOREIGN KEY (settings_id) REFERENCES settings (id) ON DELETE CASCADE
);

CREATE INDEX idx_bookmarks_settings_id ON bookmarks (settings_id);