--liquibase formatted sql
--changeset dnoulet:V1_000007__create_publications_and_levels_and_positions_table

CREATE TABLE positions
(
    id         UUID PRIMARY KEY,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_by VARCHAR(255),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    position   VARCHAR(255) NOT NULL
);

CREATE TABLE levels
(
    id         UUID PRIMARY KEY,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_by VARCHAR(255),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    level      VARCHAR(255) NOT NULL
);

CREATE TABLE publications
(
    id          UUID PRIMARY KEY,
    created_by  VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_by  VARCHAR(255),
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    title       VARCHAR(255),
    description TEXT,
    proposal    TEXT,
    position_id UUID NOT NULL,
    level_id    UUID NOT NULL,

    CONSTRAINT fk_publications_position FOREIGN KEY (position_id) REFERENCES positions (id),
    CONSTRAINT fk_publications_level FOREIGN KEY (level_id) REFERENCES levels (id)
);

CREATE INDEX idx_publications_position_id ON publications (position_id);
CREATE INDEX idx_publications_level_id ON publications (level_id);


--changeset dnoulet:V1_000007__create_publications_and_levels_and_positions_table_data context:lcl
INSERT INTO levels (id, created_by, level)
VALUES ('2efffe3c-e082-4dec-91d4-61ae0b7b0209', 'admin', 'MEDIOR');

INSERT INTO levels (id, created_by, created_at, updated_by, updated_at, level)
VALUES ('bf2a49d4-3eb1-410c-97b3-c0cd287cc155', 'admin', '2026-05-16 18:42:44.389290', 'admin',
        '2026-05-16 18:42:44.389290', 'SENIOR');


INSERT INTO positions (id, created_by, created_at, updated_by, updated_at, position)
VALUES ('067bc3ec-cd0c-4e0f-b4cf-038f79f63171', 'admin', '2026-05-16 18:42:47.335156', 'admin',
        '2026-05-16 18:42:47.335156', 'Enterprise Architecture');

INSERT INTO positions (id, created_by, position)
VALUES ('56091d8b-a9b8-47fb-b233-1448d38d3772', 'admin', 'SOL_DES');

INSERT INTO publications (id, created_by, title, description, proposal, position_id,
                          level_id)
VALUES ('b6882b57-f745-45f2-adbe-4e22dce0ae52', 'admin', 'Solution Designer', 'In need for a good developer',
        'A lot of money',
        '56091d8b-a9b8-47fb-b233-1448d38d3772', '2efffe3c-e082-4dec-91d4-61ae0b7b0209');

INSERT INTO publications (id, created_by, title, description, proposal, position_id, level_id)
VALUES ('b9049416-65d3-469b-ab9f-48d9b63720be', 'admin', 'Project Officer – Enterprise Architecture & QA Support', 'Are you someone who thrives on structure, follow-up, and making sure things are done properly?
Then this role might be a great fit for you.

At Ypto (the IT division of NMBS/SNCB), we are looking for a Project Officer to support our Enterprise Architecture Quality Assurance & Governance activities. You will play a key supporting role in ensuring that architectural decision-making, governance processes, and reporting run smoothly and consistently.

This is an excellent opportunity for someone early in their career who wants to gain exposure to enterprise level IT governance, while contributing in a very concrete, hands-on way.


Your role

You will be a reliable operational backbone for our architecture and QA activities, with a strong focus on follow-up, tooling, and process discipline.

Your responsibilities include:


Supporting Architecture Governance

Preparing, coordinating, and following up on Architecture Council meetings

Structuring agendas, documenting decisions, actions, and outcomes

Maintaining decision logs, trackers, and reference documentation

Project & Process Support

Supporting architecture related projects with structured follow-up

Tracking actions, deadlines, approvals, and dependencies

Helping to keep processes clear, UpToDate, and consistently applied

Tooling & Information Management

Maintaining and improving SharePoint sites, libraries, and document structures

Managing lists, trackers, and registers (decisions, waivers, reviews, actions, KPIs)

Ensuring information is easy to find, up to date, and correctly structured

Reporting & Automation (Low Code)

Creating and maintaining Excel based trackers and reports

Supporting automation and reporting using Power BI. Experience with Power Platform (Power Apps, Power Automate) is a plus.

Helping standardize templates, dashboards, and QA deliverables

Communication & Coordination

Acting as a clear point of contact for practical questions on process and tooling

Communicating clearly with architects, project managers, and stakeholders

Helping translate complex governance or architecture topics into structured, understandable outputs

Requirements
What we are looking for

You don’t need to be a senior technologist — accuracy, structure, and reliability matter most.

You are someone who:

Has strong written and oral communication skills (Languages: Dutch or French, and English)

Is detail oriented and enjoys meticulous follow-up

Works comfortably with Excel, SharePoint, and Office 365

Likes bringing structure to information and processes

Communicates clearly and professionally, both written and verbally

Is eager to learn and grow in an enterprise IT context

Your background

Graduate or bachelors degree (or equivalent experience)

An interest in IT- architecture, or digital transformation is important

1-3 years of practical experience with Excel and SharePoint, Power BI is a must

Familiarity with ServiceNow or governance processes is a plus

Basic understanding of software, applications, or IT-landscapes is helpful
(programming knowledge is not required, but understanding how things fit together is)', 'What we offer

A hands-on role at the heart of enterprise IT governance

Exposure to enterprise level decision-making and architecture processes

A structured environment where quality, clarity, and professionalism matter

Opportunities to grow towards more advanced roles (QA, governance, architecture support, or project coordination)

A collaborative and respectful working culture aligned with our core values:

Professionalism

Respect

Collaboration

Openness to improvement and innovation

Our Ypto offer

At Ypto, you’ll join an open corporate culture and contribute to our digital transformation. You’ll work in a role with real social impact and plenty of room to make your own mark. Alongside a healthy work-life balance and a competitive salary, we offer:

hybrid working, with a balanced weekly mix of office and remote work, combined with flexible hours

35 days of annual leave

a target bonus

a comprehensive insurance package, with hospitalisation and dental care for the whole family

coverage of outpatient medical costs

group insurance, including a supplementary pension (cafeteria plan)

meal vouchers & eco vouchers

net allowances for hybrid working and an internet allowance

', '067bc3ec-cd0c-4e0f-b4cf-038f79f63171', 'bf2a49d4-3eb1-410c-97b3-c0cd287cc155');

