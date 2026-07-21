--liquibase formatted sql
--changeset dnoulet:V1_000004__create_translationsions
CREATE TABLE keys
(
    ID         UUID PRIMARY KEY,
    key        VARCHAR(255) NOT NULL,
    created_by VARCHAR(50)  NOT NULL,
    updated_by VARCHAR(50),
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE
);


CREATE TABLE translations
(
    ID         UUID PRIMARY KEY,
    key_id     UUID         NOT NULL,
    value      varchar(355) NOT NULL,
    language   varchar(2)   NOT NULL,
    created_by VARCHAR(50)  NOT NULL,
    updated_by VARCHAR(50),
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE
);

--changeset dnoulet:V1_000004__create_translations_data context:lcl
INSERT INTO keys (id, key, created_by)
VALUES ('87581c53-8251-4b1a-9c3b-7865b00b862c', 'label.SINGLE_CHOICE', 'system');
INSERT INTO keys (id, key, created_by)
VALUES ('b953d76e-0b83-46e4-bdeb-53341a6e66b4', 'label.MULTIPLE_CHOICE', 'system');
INSERT INTO keys (id, key, created_by)
VALUES ('6b301e95-00c5-4645-bd2f-5439fa7676a4', 'label.OPEN', 'system');
INSERT INTO keys (id, key, created_by)
VALUES ('21181ad3-ebb2-42bc-b1af-e3bb7a56fb94', 'key.four', 'system');
INSERT INTO keys (id, key, created_by)
VALUES ('afe84ecf-bc35-47b2-9541-4f31864ff9c0', 'key.six', 'system');


INSERT INTO translations (id, key_id, value, language, created_by)
VALUES ('8a5e12d1-bb81-4d67-a6b3-f9ffb2f0bab5', '87581c53-8251-4b1a-9c3b-7865b00b862c', 'Single Choice', 'en',
        'system');
INSERT INTO translations (id, key_id, value, language, created_by)
VALUES ('cb580b19-391e-4ff7-a2eb-48a248027908', 'b953d76e-0b83-46e4-bdeb-53341a6e66b4', 'Multiple Choice', 'en',
        'system');
INSERT INTO translations (id, key_id, value, language, created_by)
VALUES ('847e21e9-d377-4659-a1b1-f8f005e88a09', '6b301e95-00c5-4645-bd2f-5439fa7676a4', 'Open', 'en', 'system');
INSERT INTO translations (id, key_id, value, language, created_by)
VALUES ('34029675-8e34-4339-9b87-2a7879c2f17e', '21181ad3-ebb2-42bc-b1af-e3bb7a56fb94', 'four', 'en', 'system');
INSERT INTO translations (id, key_id, value, language, created_by)
VALUES ('f927241c-e450-408c-b798-2bed5e4dc106', 'afe84ecf-bc35-47b2-9541-4f31864ff9c0', 'six', 'en', 'system');
