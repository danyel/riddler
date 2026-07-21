--liquibase formatted sql
--changeset dnoulet:V1_000011__create_answer_solutions_table
CREATE TABLE answer_solutions
(
    id         UUID PRIMARY KEY,
    answer_id  UUID         NOT NULL,
    value      VARCHAR(255) NOT NULL,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE default now(),
    updated_by VARCHAR(255),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_solutions_answer FOREIGN KEY (answer_id) REFERENCES answers (id) ON DELETE CASCADE
);
ALTER TABLE answers
    DROP COLUMN value;
CREATE INDEX idx_solutions_answer_id ON answer_solutions (answer_id);
CREATE INDEX idx_solutions_word_lower ON answer_solutions (LOWER(value));

