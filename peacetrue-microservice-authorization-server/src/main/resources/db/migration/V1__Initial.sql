create table users
(
    username varchar(50)  not null primary key,
    password varchar(255) not null,
    enabled  boolean      not null
);

create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);
create unique index ix_auth_username on authorities (username, authority);


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


-- used in tests that use mysql
drop table if exists oauth_client_details;
create table oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
);

drop table if exists oauth_client_token;
create table oauth_client_token
(
    token_id          VARCHAR(256),
    token             VARBINARY(10240),
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);

drop table if exists oauth_access_token;
create table oauth_access_token
(
    token_id          VARCHAR(256),
    token             VARBINARY(10240),
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    VARBINARY(10240),
    refresh_token     VARCHAR(256)
);

drop table if exists oauth_refresh_token;
create table oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          VARBINARY(10240),
    authentication VARBINARY(10240)
);

drop table if exists oauth_code;
create table oauth_code
(
    code           VARCHAR(256),
    authentication VARBINARY(10240)
);

drop table if exists oauth_approvals;
create table oauth_approvals
(
    userId         VARCHAR(256),
    clientId       VARCHAR(256),
    scope          VARCHAR(256),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

INSERT INTO users (username, password, enabled)
VALUES ('peacetrue', '{noop}password', 1);

insert into authorities (username, authority)
values ('peacetrue', 'USER');

insert into oauth_client_details (client_id, client_secret, authorized_grant_types, authorities, resource_ids, scope,
                                  web_server_redirect_uri)
values ('peacetrue', '{noop}password', 'authorization_code,password,client_credentials,implicit,refresh_token', 'USER',
        null, null, 'http://localhost:8530/login/oauth2/code/peacetrue'),
       ('reader', '{noop}secret', 'authorization_code,password,client_credentials,implicit,refresh_token', 'USER',
        'peacetrue-microservice-resource-server', 'message:read', 'http://localhost:8530/login/oauth2/code/peacetrue'),
       ('writer', '{noop}secret', 'authorization_code,password,client_credentials,implicit,refresh_token', 'USER',
        'peacetrue-microservice-resource-server', 'message:write', 'http://localhost:8530/login/oauth2/code/peacetrue'),
       ('noscopes', '{noop}secret', 'authorization_code,password,client_credentials,implicit,refresh_token', 'USER',
        'peacetrue-microservice-resource-server', 'none', 'http://localhost:8530/login/oauth2/code/peacetrue')
;


