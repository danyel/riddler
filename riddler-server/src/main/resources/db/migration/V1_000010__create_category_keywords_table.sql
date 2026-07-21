--liquibase formatted sql
--changeset dnoulet:V1_000010__create_category_keywords_table
CREATE TABLE category_keywords
(
    id          UUID PRIMARY KEY,
    category_id UUID         NOT NULL,
    word        VARCHAR(255) NOT NULL,
    CONSTRAINT fk_keywords_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE INDEX idx_keywords_category_id ON category_keywords (category_id);
CREATE INDEX idx_keywords_word_lower ON category_keywords (LOWER(word));


--changeset dnoulet:V1_000010__create_category_keywords_data context:lcl
-- 1. INSERT THE MAIN IT DOMAIN CATEGORIES
INSERT INTO categories (id, name, created_by, created_at, updated_by, updated_at)
VALUES ('11111111-1111-1111-1111-111111111111', 'Software Engineering & Languages', 'system_admin', NOW(),
        'system_admin', NOW()),
       ('22222222-2222-2222-2222-222222222222', 'Frameworks & Libraries', 'system_admin', NOW(), 'system_admin', NOW()),
       ('33333333-3333-3333-3333-333333333333', 'Security, Identity & Auth', 'system_admin', NOW(), 'system_admin',
        NOW()),
       ('44444444-4444-4444-4444-444444444444', 'Data Science & Databases', 'system_admin', NOW(), 'system_admin',
        NOW()),
       ('55555555-5555-5555-5555-555555555555', 'DevOps, Cloud & Infrastructure', 'system_admin', NOW(), 'system_admin',
        NOW()),
       ('66666666-6666-6666-6666-666666666666', 'Seniority, Roles & Experience', 'system_admin', NOW(), 'system_admin',
        NOW());

-- 2. KEYWORDS FOR: Software Engineering & Languages (11111111-...)
INSERT INTO category_keywords (id, category_id, word)
VALUES (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'java 17'),
       (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'java 21'),
       (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'typescript'),
       (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'python 3'),
       (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'golang'),
       (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'rust'),
       (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'c#'),
       (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'object-oriented programming'),
       (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'functional programming');

-- 3. KEYWORDS FOR: Frameworks & Libraries (22222222-...)
INSERT INTO category_keywords (id, category_id, word)
VALUES (gen_random_uuid(), '22222222-2222-2222-2222-222222222222', 'spring boot'),
       (gen_random_uuid(), '22222222-2222-2222-2222-222222222222', 'spring data jpa'),
       (gen_random_uuid(), '22222222-2222-2222-2222-222222222222', 'vaadin hilla'),
       (gen_random_uuid(), '22222222-2222-2222-2222-222222222222', 'react functional components'),
       (gen_random_uuid(), '22222222-2222-2222-2222-222222222222', 'next.js'),
       (gen_random_uuid(), '22222222-2222-2222-2222-222222222222', 'angular'),
       (gen_random_uuid(), '22222222-2222-2222-2222-222222222222', 'express.js'),
       (gen_random_uuid(), '22222222-2222-2222-2222-222222222222', 'hibernate orm'),
       (gen_random_uuid(), '22222222-2222-2222-2222-222222222222', 'redux toolkit');

-- 4. KEYWORDS FOR: Security, Identity & Auth (33333333-...)
INSERT INTO category_keywords (id, category_id, word)
VALUES (gen_random_uuid(), '33333333-3333-3333-3333-333333333333', 'oauth2'),
       (gen_random_uuid(), '33333333-3333-3333-3333-333333333333', 'openid connect'),
       (gen_random_uuid(), '33333333-3333-3333-3333-333333333333', 'jwt token'),
       (gen_random_uuid(), '33333333-3333-3333-3333-333333333333', 'spring security'),
       (gen_random_uuid(), '33333333-3333-3333-3333-333333333333', 'keycloak'),
       (gen_random_uuid(), '33333333-3333-3333-3333-333333333333', 'role-based access control'),
       (gen_random_uuid(), '33333333-3333-3333-3333-333333333333', 'mfa authentication'),
       (gen_random_uuid(), '33333333-3333-3333-3333-333333333333', 'saml 2.0'),
       (gen_random_uuid(), '33333333-3333-3333-3333-333333333333', 'tls encryption');

-- 5. KEYWORDS FOR: Data Science & Databases (44444444-...)
INSERT INTO category_keywords (id, category_id, word)
VALUES (gen_random_uuid(), '44444444-4444-4444-4444-444444444444', 'postgresql'),
       (gen_random_uuid(), '44444444-4444-4444-4444-444444444444', 'mongodb aggregation'),
       (gen_random_uuid(), '44444444-4444-4444-4444-444444444444', 'redis caching'),
       (gen_random_uuid(), '44444444-4444-4444-4444-444444444444', 'database indexing'),
       (gen_random_uuid(), '44444444-4444-4444-4444-444444444444', 'apache kafka'),
       (gen_random_uuid(), '44444444-4444-4444-4444-444444444444', 'elasticsearch'),
       (gen_random_uuid(), '44444444-4444-4444-4444-444444444444', 'pandas python'),
       (gen_random_uuid(), '44444444-4444-4444-4444-444444444444', 'machine learning models');

-- 6. KEYWORDS FOR: DevOps, Cloud & Infrastructure (55555555-...)
INSERT INTO category_keywords (id, category_id, word)
VALUES (gen_random_uuid(), '55555555-5555-5555-5555-555555555555', 'docker compose'),
       (gen_random_uuid(), '55555555-5555-5555-5555-555555555555', 'kubernetes cluster'),
       (gen_random_uuid(), '55555555-5555-5555-5555-555555555555', 'github actions ci/cd'),
       (gen_random_uuid(), '55555555-5555-5555-5555-555555555555', 'terraform iac'),
       (gen_random_uuid(), '55555555-5555-5555-5555-555555555555', 'aws ec2 s3'),
       (gen_random_uuid(), '55555555-5555-5555-5555-555555555555', 'azure devops'),
       (gen_random_uuid(), '55555555-5555-5555-5555-555555555555', 'linux server management'),
       (gen_random_uuid(), '55555555-5555-5555-5555-555555555555', 'nginx reverse proxy');

-- 7. KEYWORDS FOR: Seniority, Roles & Experience (66666666-...)
INSERT INTO category_keywords (id, category_id, word)
VALUES (gen_random_uuid(), '66666666-6666-6666-6666-666666666666', 'junior developer 1-2 years'),
       (gen_random_uuid(), '66666666-6666-6666-6666-666666666666', 'mid-level engineer 3-5 years'),
       (gen_random_uuid(), '66666666-6666-6666-6666-666666666666', 'senior developer 5+ years'),
       (gen_random_uuid(), '66666666-6666-6666-6666-666666666666', 'senior staff engineer 10+ years'),
       (gen_random_uuid(), '66666666-6666-6666-6666-666666666666', 'software architect'),
       (gen_random_uuid(), '66666666-6666-6666-6666-666666666666', 'devops specialist'),
       (gen_random_uuid(), '66666666-6666-6666-6666-666666666666', 'lead engineering manager'),
       (gen_random_uuid(), '66666666-6666-6666-6666-666666666666', 'cto technical director');

