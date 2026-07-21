--liquibase formatted sql
--changeset dnoulet:V1_000001_create_questions
CREATE TABLE answers
(
    ID          UUID PRIMARY KEY,
    VALUE       VARCHAR(255) NOT NULL,
    QUESTION_ID UUID         NOT NULL,
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE
);

--changeset dnoulet:V1_000001_create_questions_data context:lcl
INSERT INTO answers (id, value, question_id)
VALUES ('a9e8cbaa-e047-41bb-9554-d4b08352a08a', 'Answer 1',
        'a2b20b03-9d96-41d4-8e83-a35b21c21fe7');
INSERT INTO answers (id, value, question_id)
VALUES ('18adc354-d7ea-4f97-a579-5628f3cfa0cb', 'Answer 2',
        'a2b20b03-9d96-41d4-8e83-a35b21c21fe7');
INSERT INTO answers (id, value, question_id)
VALUES ('a599e7e2-2e67-4638-9638-d47e82f909d1', 'Answer 3',
        'a2b20b03-9d96-41d4-8e83-a35b21c21fe7');
INSERT INTO answers (id, value, question_id)
VALUES ('b241a75d-ebc7-4991-b941-d8205bc5e931', 'Answer',
        '6d2c7dee-740a-47f1-99b0-4e4167182ae7');
INSERT INTO answers (id, value, question_id)
VALUES ('506f3187-ecc2-452e-bd3b-b48e11360d2c', 'Long Answer',
        '3cee42fb-d8da-4127-8f0e-3ddfab263f34');
