DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    user_id     BIGSERIAL    NOT NULL,
    username    VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(50)  NOT NULL,
    enabled     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP
);

-- Primary key constraints
ALTER TABLE users
    ADD CONSTRAINT pk_users
        PRIMARY KEY (user_id);

-- Unique constraints
ALTER TABLE users
    ADD CONSTRAINT uq_users_username
        UNIQUE (username);

ALTER TABLE users
    ADD CONSTRAINT uq_users_email
        UNIQUE (email);
