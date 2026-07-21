--liquibase formatted sql
--changeset dnoulet:V1_000005__create_activities
CREATE TABLE activities
(
    ID              UUID PRIMARY KEY,
    question_id     UUID         NOT NULL,
    element_id      VARCHAR(255) NOT NULL,
    action_type     VARCHAR(255) NOT NULL,
    additional_data TEXT         NOT NULL,
    action_time TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(50) NOT NULL,
    updated_by VARCHAR(50),
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE
);
