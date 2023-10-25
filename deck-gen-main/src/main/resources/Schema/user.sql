drop table user;

CREATE TABLE user (
    user_id VARCHAR(36),
    email VARCHAR(100),
    admin TINYINT(1) DEFAULT FALSE,
    moderator TINYINT(1) DEFAULT FALSE,
    verified TINYINT(1) NOT NULL DEFAULT FALSE,
    enabled TINYINT(1) NOT NULL DEFAULT TRUE,
    password VARCHAR(255),
    name VARCHAR(50),
    tokens BIGINT
);

