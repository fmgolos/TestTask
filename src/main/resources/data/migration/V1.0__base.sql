create table if not exists credentials
(
    id       SERIAL PRIMARY KEY,
    login    TEXT   not null,
    password TEXT   not null,
    CONSTRAINT constraint_name UNIQUE (login)
);