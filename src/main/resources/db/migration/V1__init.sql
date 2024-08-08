create table clients
(
    id      bigint primary key,
    api_key varchar(255) not null
);

insert into clients
values (1, '123');
insert into clients
values (2, '456');