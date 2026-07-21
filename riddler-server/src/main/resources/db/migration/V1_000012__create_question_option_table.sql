--liquibase formatted sql
--changeset dnoulet:V1_000012__create_question_options_table
CREATE TABLE question_options
(
    id          UUID PRIMARY KEY,
    question_id UUID         NOT NULL,
    value       VARCHAR(255) NOT NULL,
    created_by  VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE default now(),
    updated_by  VARCHAR(255),
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_options_question FOREIGN KEY (question_id) REFERENCES questions (id) ON DELETE CASCADE
);

CREATE INDEX idx_options_question_id ON question_options (question_id);
CREATE INDEX idx_options_value_lower ON question_options (LOWER(value));

