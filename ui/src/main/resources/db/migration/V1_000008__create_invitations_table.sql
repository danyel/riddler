--liquibase formatted sql
--changeset dnoulet:V1_000008__create_invitations
CREATE TABLE invitations
(
    ID             UUID PRIMARY KEY,
    participant_id UUID         NOT NULL,
    publication_id UUID         NOT NULL,
    stored_token   VARCHAR(255) NULL,
    created_by     VARCHAR(50)  NOT NULL,
    updated_by     VARCHAR(50),
    CREATED_AT     TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_invitations_publications FOREIGN KEY (publication_id)
        REFERENCES publications (id) ON DELETE CASCADE
);

CREATE TABLE invitation_questions
(
    invitation_id UUID NOT NULL,
    question_id   UUID NOT NULL,
    CONSTRAINT fk_invitation FOREIGN KEY (invitation_id)
        REFERENCES invitations (id) ON DELETE CASCADE,
    PRIMARY KEY (invitation_id, question_id)
);

ALTER TABLE answers
    ADD COLUMN invitation_id UUID;
ALTER TABLE answers
    ADD CONSTRAINT fk_answers_invitation FOREIGN KEY (invitation_id) REFERENCES invitations (id);

CREATE INDEX idx_invitation_questions_invitation ON invitation_questions (invitation_id);

--changeset dnoulet:V1_000008__create_invitationsdata context:lcl
INSERT INTO invitations (id, participant_id, publication_id, stored_token, created_by, updated_by, created_at,
                         updated_at)
VALUES ('d376b894-b259-4714-87c6-cf411e5b10db', 'dbc132cc-b918-40fa-b2e9-81032635c4e8',
        'b9049416-65d3-469b-ab9f-48d9b63720be', '8Am9x_t3eVokFwF6', 'admin', 'admin', '2026-05-24 21:28:25.744614',
        '2026-05-24 21:37:35.320286');
UPDATE answers
set invitation_id = 'd376b894-b259-4714-87c6-cf411e5b10db'
where invitation_id is null;