-- =====================================================================
-- CA firm website + back office: base schema (MySQL 8)
-- Flyway owns the schema; Hibernate runs with ddl-auto=none.
-- =====================================================================

-- ---------------------------------------------------------------------
-- Back office
-- ---------------------------------------------------------------------
CREATE TABLE admin_user (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    username        VARCHAR(60)  NOT NULL,
    password_hash   VARCHAR(100) NOT NULL,
    full_name       VARCHAR(120) NOT NULL,
    email           VARCHAR(120) NOT NULL,
    role            VARCHAR(20)  NOT NULL,
    enabled         BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login_at   TIMESTAMP    NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_admin_user_username UNIQUE (username),
    CONSTRAINT ck_admin_user_role CHECK (role IN ('ADMIN', 'STAFF'))
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Site content
-- ---------------------------------------------------------------------
CREATE TABLE office_location (
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    city              VARCHAR(80)  NOT NULL,
    slug              VARCHAR(80)  NOT NULL,
    hero_heading      VARCHAR(160) NULL,
    intro             TEXT         NULL,
    areas_served      VARCHAR(300) NULL,
    address_line      VARCHAR(300) NOT NULL,
    phone             VARCHAR(20)  NULL,
    email             VARCHAR(120) NULL,
    office_hours      VARCHAR(120) NULL,
    map_url           VARCHAR(500) NULL,
    meta_title        VARCHAR(160) NULL,
    meta_description  VARCHAR(300) NULL,
    head_office       BOOLEAN      NOT NULL DEFAULT FALSE,
    active            BOOLEAN      NOT NULL DEFAULT TRUE,
    sort_order        INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uq_office_location_slug UNIQUE (slug)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE service_category (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    name        VARCHAR(80) NOT NULL,
    slug        VARCHAR(80) NOT NULL,
    sort_order  INT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uq_service_category_slug UNIQUE (slug)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE firm_service (
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    category_id       BIGINT       NULL,
    title             VARCHAR(120) NOT NULL,
    slug              VARCHAR(120) NOT NULL,
    summary           VARCHAR(400) NOT NULL,
    body              TEXT         NULL,
    icon              VARCHAR(40)  NULL,
    meta_title        VARCHAR(160) NULL,
    meta_description  VARCHAR(300) NULL,
    featured          BOOLEAN      NOT NULL DEFAULT FALSE,
    active            BOOLEAN      NOT NULL DEFAULT TRUE,
    sort_order        INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uq_firm_service_slug UNIQUE (slug),
    CONSTRAINT fk_firm_service_category FOREIGN KEY (category_id) REFERENCES service_category (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE service_highlight (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    service_id  BIGINT       NOT NULL,
    text        VARCHAR(300) NOT NULL,
    sort_order  INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_service_highlight_service FOREIGN KEY (service_id) REFERENCES firm_service (id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE industry (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(80)  NOT NULL,
    slug        VARCHAR(80)  NOT NULL,
    summary     VARCHAR(300) NULL,
    icon        VARCHAR(40)  NULL,
    sort_order  INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uq_industry_slug UNIQUE (slug)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- location_id NULL = shown for every city; service_id NULL = general FAQ
CREATE TABLE faq (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    question     VARCHAR(300) NOT NULL,
    answer       TEXT         NOT NULL,
    location_id  BIGINT       NULL,
    service_id   BIGINT       NULL,
    active       BOOLEAN      NOT NULL DEFAULT TRUE,
    sort_order   INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_faq_location FOREIGN KEY (location_id) REFERENCES office_location (id),
    CONSTRAINT fk_faq_service FOREIGN KEY (service_id) REFERENCES firm_service (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE testimonial (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    author_name   VARCHAR(120) NOT NULL,
    author_title  VARCHAR(160) NULL,
    quote         TEXT         NOT NULL,
    rating        INT          NULL,
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    sort_order    INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT ck_testimonial_rating CHECK (rating IS NULL OR rating BETWEEN 1 AND 5)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE team_member (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    full_name       VARCHAR(120) NOT NULL,
    designation     VARCHAR(120) NOT NULL,
    qualifications  VARCHAR(160) NULL,
    bio             TEXT         NULL,
    photo_url       VARCHAR(500) NULL,
    location_id     BIGINT       NULL,
    active          BOOLEAN      NOT NULL DEFAULT TRUE,
    sort_order      INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_team_member_location FOREIGN KEY (location_id) REFERENCES office_location (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE article (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    title         VARCHAR(200) NOT NULL,
    slug          VARCHAR(200) NOT NULL,
    summary       VARCHAR(500) NULL,
    body          TEXT         NULL,
    category      VARCHAR(60)  NULL,
    author        VARCHAR(120) NULL,
    status        VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    published_at  TIMESTAMP    NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_article_slug UNIQUE (slug),
    CONSTRAINT ck_article_status CHECK (status IN ('DRAFT', 'PUBLISHED')),
    INDEX ix_article_status (status),
    INDEX ix_article_published_at (published_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE compliance_deadline (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    due_date    DATE         NOT NULL,
    title       VARCHAR(200) NOT NULL,
    law         VARCHAR(20)  NOT NULL,
    applies_to  VARCHAR(200) NULL,
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    CONSTRAINT ck_compliance_deadline_law CHECK (law IN ('GST', 'TDS', 'INCOME_TAX', 'ROC')),
    INDEX ix_compliance_deadline_due_date (due_date)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Leads
-- ---------------------------------------------------------------------
CREATE TABLE enquiry (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    reference_no    VARCHAR(20)  NULL,
    full_name       VARCHAR(120) NOT NULL,
    email           VARCHAR(120) NOT NULL,
    phone           VARCHAR(20)  NOT NULL,
    company_name    VARCHAR(160) NULL,
    service_id      BIGINT       NULL,
    location_id     BIGINT       NULL,
    message         TEXT         NULL,
    source_page     VARCHAR(255) NULL,
    status          VARCHAR(20)  NOT NULL DEFAULT 'NEW',
    internal_notes  TEXT         NULL,
    assigned_to_id  BIGINT       NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_enquiry_reference_no UNIQUE (reference_no),
    CONSTRAINT fk_enquiry_service FOREIGN KEY (service_id) REFERENCES firm_service (id),
    CONSTRAINT fk_enquiry_location FOREIGN KEY (location_id) REFERENCES office_location (id),
    CONSTRAINT fk_enquiry_assigned_to FOREIGN KEY (assigned_to_id) REFERENCES admin_user (id),
    CONSTRAINT ck_enquiry_status CHECK (status IN ('NEW', 'CONTACTED', 'PROPOSAL_SENT', 'CONVERTED', 'CLOSED')),
    INDEX ix_enquiry_status (status),
    INDEX ix_enquiry_created_at (created_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE newsletter_subscriber (
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    email            VARCHAR(120) NOT NULL,
    active           BOOLEAN      NOT NULL DEFAULT TRUE,
    subscribed_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    unsubscribed_at  TIMESTAMP    NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_newsletter_subscriber_email UNIQUE (email)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Careers
-- ---------------------------------------------------------------------
CREATE TABLE job_opening (
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    title             VARCHAR(160) NOT NULL,
    slug              VARCHAR(160) NOT NULL,
    location_id       BIGINT       NULL,
    department        VARCHAR(80)  NULL,
    experience_range  VARCHAR(60)  NULL,
    employment_type   VARCHAR(20)  NOT NULL,
    description       TEXT         NULL,
    active            BOOLEAN      NOT NULL DEFAULT TRUE,
    posted_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    closes_on         DATE         NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_job_opening_slug UNIQUE (slug),
    CONSTRAINT fk_job_opening_location FOREIGN KEY (location_id) REFERENCES office_location (id),
    CONSTRAINT ck_job_opening_type CHECK (employment_type IN ('FULL_TIME', 'ARTICLESHIP', 'INTERNSHIP'))
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE job_application (
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    job_id         BIGINT       NOT NULL,
    full_name      VARCHAR(120) NOT NULL,
    email          VARCHAR(120) NOT NULL,
    phone          VARCHAR(20)  NOT NULL,
    qualification  VARCHAR(80)  NULL,
    resume_path    VARCHAR(500) NULL,
    cover_note     TEXT         NULL,
    status         VARCHAR(20)  NOT NULL DEFAULT 'RECEIVED',
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_job_application_job FOREIGN KEY (job_id) REFERENCES job_opening (id),
    CONSTRAINT ck_job_application_status CHECK (status IN ('RECEIVED', 'SHORTLISTED', 'INTERVIEW_SCHEDULED', 'OFFERED', 'HIRED', 'REJECTED')),
    INDEX ix_job_application_status (status)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
