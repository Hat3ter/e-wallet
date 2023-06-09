create table if not exists wallets
(
    id            uuid not null
        primary key,
    balance       numeric(38, 2),
    currency_type varchar(255),
    name          varchar(255),
    user_id            uuid not null
);

create table if not exists users
(
    id       uuid not null
        primary key,
    login    varchar(255),
    password varchar(255),
    role varchar(255)
);


create extension if not exists "uuid-ossp";

insert into users(id, login, password, role)
values (uuid_generate_v4(), 'admin', '$2a$10$hdxa9S.O7TVShSZbQVcF4.JY5Pdd0vhJ9a6TdaaYQ.7LnuDKXPvWm', 'ADMIN')