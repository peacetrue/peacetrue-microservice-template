create table `groups`
(
    id         bigint auto_increment primary key,
    group_name varchar(50) not null
);

create table group_authorities
(
    group_id  bigint      not null,
    authority varchar(50) not null,
    constraint fk_group_authorities_group foreign key (group_id) references `groups` (id)
);

create table group_members
(
    id       bigint auto_increment primary key,
    username varchar(50) not null,
    group_id bigint      not null,
    constraint fk_group_members_group foreign key (group_id) references `groups` (id)
);
