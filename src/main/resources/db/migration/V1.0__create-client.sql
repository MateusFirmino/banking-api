CREATE TABLE client
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name           VARCHAR(255),
    age            INTEGER                                 NOT NULL,
    email          VARCHAR(255),
    account_number VARCHAR(255),
    CONSTRAINT pk_client PRIMARY KEY (id)
);