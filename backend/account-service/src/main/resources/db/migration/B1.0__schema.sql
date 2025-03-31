DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS role_dict CASCADE;

CREATE TABLE users
(
    user_id     BIGSERIAL    NOT NULL,
    username    VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role_code   VARCHAR(50)  NOT NULL,
    enabled     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP
);

CREATE TABLE role_dict
(
    code       VARCHAR(50)  NOT NULL,
    name       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Primary key constraints
ALTER TABLE users
    ADD CONSTRAINT pk_users
        PRIMARY KEY (user_id);

ALTER TABLE role_dict
    ADD CONSTRAINT pk_role_dict
        PRIMARY KEY (code);

-- Foreign key constraints
ALTER TABLE users
    ADD CONSTRAINT fk_users_role
        FOREIGN KEY (role_code) REFERENCES role_dict (code);

-- Unique constraints
ALTER TABLE users
    ADD CONSTRAINT uq_users_username
        UNIQUE (username);

ALTER TABLE users
    ADD CONSTRAINT uq_users_email
        UNIQUE (email);

-- Indexes
CREATE INDEX idx_users_role_code
    ON users (role_code);
