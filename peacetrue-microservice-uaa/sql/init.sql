# 添加组：message.read 和 message.write
insert into `groups` (id, displayName, lastModified)
values ('ee3ba16a-1de9-4986-af85-d9c5bac0adf8', 'message.read', CURRENT_TIMESTAMP);

insert into `groups` (id, displayName, lastModified)
values ('641bd159-51fd-4389-bd5d-116fb3d57583', 'message.write', CURRENT_TIMESTAMP);

# 添加客户端
insert into oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types,
                                  web_server_redirect_uri, authorities, access_token_validity, additional_information,
                                  autoapprove,
                                  identity_zone_id, lastmodified, show_on_home_page)
values ('peacetrue', 'peacetrue-microservice-resource-server',
        '$2a$10$rzqpwIn6Whkp4XWBJOoEnO2B0P9VP4qq8Tv1pZPEPEVJOnjgu5Kwi', 'openid,message.read,message.write',
        'authorization_code,client_credentials',
        'http://localhost:8530/**,http://localhost:8531/**,https://localhost:8530/**,https://localhost:8531/**,http://peacetrue.cn:8530/**,http://peacetrue.cn:8531/**,https://peacetrue.cn:8530/**,https://peacetrue.cn:8531/**',
        'message.read、message.write', 6000, null, false, 'uaa', CURRENT_TIMESTAMP, 1);

# 添加用户
insert into users (id, created, lastModified, version, username, password, email, authorities, givenName, familyName,
                   active, phoneNumber, verified, origin, identity_zone_id, salt,
                   legacy_verification_behavior, passwd_change_required)
values ('0691ccf3-b63f-4aac-a188-17ce1d014038', CURRENT_TIMESTAMP, null, 0, 'peacetrue',
        '$2a$10$rzqpwIn6Whkp4XWBJOoEnO2B0P9VP4qq8Tv1pZPEPEVJOnjgu5Kwi',
        'peacetrue@peacetrue.cn', 'uaa.user',
        'peacetrue',
        'peacetrue', 1,
        '18600000000', 1, 'uaa', 'uaa', 0, 0, 0);

# 为用户赋予权限
insert into group_membership (group_id, member_id, identity_zone_id)
values ('4c8fbd94-e34e-49a8-990a-80e94bb34974', '0691ccf3-b63f-4aac-a188-17ce1d014038', 'uaa');
insert into group_membership (group_id, member_id, identity_zone_id)
values ('7508b654-754b-4fba-9119-1d531cb276af', '0691ccf3-b63f-4aac-a188-17ce1d014038', 'uaa');
insert into group_membership (group_id, member_id, identity_zone_id)
values ('641bd159-51fd-4389-bd5d-116fb3d57583', '0691ccf3-b63f-4aac-a188-17ce1d014038', 'uaa')
