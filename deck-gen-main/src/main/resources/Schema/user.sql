use deckgendb;

drop table users;

CREATE TABLE users (
    user_id VARCHAR(36),
    username varchar(50),
    email VARCHAR(100),
    admin TINYINT(1) DEFAULT FALSE,
	ROLE varchar(10),
    enabled TINYINT(1) NOT NULL DEFAULT TRUE,
    password VARCHAR(255),
    name VARCHAR(50),
    tokens BIGINT
);



