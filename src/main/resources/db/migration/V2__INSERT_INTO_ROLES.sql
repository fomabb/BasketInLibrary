create table public.role
(
    id   integer generated by default as identity
        primary key,
    name varchar(255)
) WITH (
      OIDS = FALSE
    );

alter table public.role
    owner to postgres;

insert into role(name)
values ('ROLE_ADMIN');

insert into role(name)
values ('ROLE_USER');
