drop table if exists "user";
create table "user" (
    id serial primary key,
    firstName varchar(255) not null,
    middleName varchar(255) null,
    lastName varchar(255) not null,
    dob date not null,
    url varchar null,
    bio varchar null
);