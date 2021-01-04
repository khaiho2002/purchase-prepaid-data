--liquibase formatted sql
--changeset khai.d.ho:1

CREATE TABLE transaction
(
    id            uuid PRIMARY KEY,
    mobile        VARCHAR(250) NOT NULL,
    code          VARCHAR(250),
    status        VARCHAR(20),
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
