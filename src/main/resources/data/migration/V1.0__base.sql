create table if not exists users
(
    id       SERIAL PRIMARY KEY,
    login    TEXT   not null,
    password TEXT   not null,
    CONSTRAINT constraint_name UNIQUE (login)
);

create table if not exists message_storage
(
    id       SERIAL PRIMARY KEY,
    login    TEXT   not null REFERENCES users (id) ON DELETE CASCADE,
    text     TEXT   not null
);

