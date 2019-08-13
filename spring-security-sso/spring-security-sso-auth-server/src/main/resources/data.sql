-- oauth client
-- The encrypted client_secret it `secret`
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity)
  VALUES ('clientId', '{bcrypt}$2a$10$vCXMWCn7fDZWOcLnIEhmK.74dvK1Eh8ae2WrWlhr2ETPLoxQctN4.', 'read,write', 'password,refresh_token,client_credentials', 'ROLE_CLIENT', 300);
 
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity, web_server_redirect_uri, autoapprove)
  VALUES ('app1', 'secret', 'user_info', 'authorization_code', 'ROLE_CLIENT', 3600, 'http://localhost:8082/ui/login', 'true');

INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity, web_server_redirect_uri, autoapprove)
  VALUES ('app2', '{bcrypt}$2a$10$w6U0a421YvG8GuVXeVcyk..7Kba/esmX0jNwV2jYYwedKC2CIT7Qy', 'user_info', 'authorization_code', 'ROLE_CLIENT', 3600, 'http://localhost:8083/ui2/login', 'true'); 
  

-- user role
INSERT INTO authority  VALUES(1,'ROLE_OAUTH_ADMIN');
INSERT INTO authority VALUES(2,'ROLE_RESOURCE_ADMIN');
INSERT INTO authority VALUES(3,'ROLE_PRODUCT_ADMIN');
INSERT INTO authority VALUES(4,'ROLE_SSS_USER');

-- users credentials

INSERT INTO credentials VALUES (1, true,'johnson','{bcrypt}$2y$12$iqgG/YvaANHWEadKgPAYCe/KlvrEuNUNBCXWjMGlZBeWcoNrpzJ0G','0');
--INSERT INTO credentials VALUES(2,b'1','resource_admin','$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2','0');
--INSERT INTO credentials  VALUES(3,b'1','product_admin','$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2','0');

-- users role

INSERT INTO credentials_authorities VALUES (1,4);
--INSERT INTO credentials_authorities VALUE (2,2);
--INSERT INTO credentials_authorities VALUE (3,3);
  
-- The encrypted password is `pass`
--INSERT INTO users (id, username, password, enabled) VALUES (1, 'user', '{bcrypt}$2a$10$cyf5NfobcruKQ8XGjUJkEegr9ZWFqaea6vjpXWEaSqTa2xL9wjgQC', 1);
--INSERT INTO users (id, username, password, enabled) VALUES (2, 'guest', '{bcrypt}$2a$10$cyf5NfobcruKQ8XGjUJkEegr9ZWFqaea6vjpXWEaSqTa2xL9wjgQC', 1);
--INSERT INTO users (id, username, password, enabled) VALUES (3, 'johnson', '{bcrypt}$2y$12$iqgG/YvaANHWEadKgPAYCe/KlvrEuNUNBCXWjMGlZBeWcoNrpzJ0G', 1);

--INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
--INSERT INTO authorities (username, authority) VALUES ('guest', 'ROLE_GUEST');
