create table if not exists credentials
(
    login    TEXT PRIMARY KEY,
    password TEXT not null
)