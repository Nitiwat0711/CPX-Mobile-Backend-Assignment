drop table if exists "user";
create table "user" (
    id serial primary key,
    "first_name" varchar(255) not null,
    "middle_name" varchar(255) null,
    "last_name" varchar(255) not null,
    "email" varchar(255) not null,
    "dob" date not null,
    "url" varchar null,
    "bio" varchar null
);