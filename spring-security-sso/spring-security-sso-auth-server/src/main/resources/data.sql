-- The encrypted client_secret it `secret`
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity)
  VALUES ('clientId', '{bcrypt}$2a$10$vCXMWCn7fDZWOcLnIEhmK.74dvK1Eh8ae2WrWlhr2ETPLoxQctN4.', 'read,write', 'password,refresh_token,client_credentials', 'ROLE_CLIENT', 300);

  
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity, web_server_redirect_uri)
  VALUES ('app1', '{bcrypt}$2y$12$K3GlJOVSwe6LHWbIG/TWW.K94ZLfQLTwat6e08z8Qc9xbakhK3bQ6', 'read,write', 'authorization_code,password,refresh_token,client_credentials', 'ROLE_CLIENT', 3600, 'http://localhost:8082/ui/login');

INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity, web_server_redirect_uri)
  VALUES ('app2', '{bcrypt}$2y$12$K3GlJOVSwe6LHWbIG/TWW.K94ZLfQLTwat6e08z8Qc9xbakhK3bQ6', 'read,write', 'authorization_code,password,refresh_token,client_credentials', 'ROLE_CLIENT', 3600, 'http://localhost:8083/ui2/login');
  
  
--        clients.inMemory()
--            .withClient("app1")
--            .secret(passwordEncoder.encode("secret"))
--            .authorizedGrantTypes("authorization_code")
--            .scopes("user_info")
--            .autoApprove(true)
--            .redirectUris("http://localhost:8082/ui/login")
--            .accessTokenValiditySeconds(3600)    
--            .and()
--	        .withClient("app2")
--	        .secret(passwordEncoder.encode("secret"))
--	        .authorizedGrantTypes("authorization_code")
--	        .scopes("user_info")
--	        .autoApprove(true)
--	        .redirectUris("http://localhost:8083/ui2/login")
--	        .accessTokenValiditySeconds(3600)                                    
--        ;  
  
-- The encrypted password is `pass`
INSERT INTO users (id, username, password, enabled) VALUES (1, 'user', '{bcrypt}$2a$10$cyf5NfobcruKQ8XGjUJkEegr9ZWFqaea6vjpXWEaSqTa2xL9wjgQC', 1);
INSERT INTO users (id, username, password, enabled) VALUES (2, 'guest', '{bcrypt}$2a$10$cyf5NfobcruKQ8XGjUJkEegr9ZWFqaea6vjpXWEaSqTa2xL9wjgQC', 1);
INSERT INTO users (id, username, password, enabled) VALUES (3, 'johnson', '{bcrypt}$2y$12$iqgG/YvaANHWEadKgPAYCe/KlvrEuNUNBCXWjMGlZBeWcoNrpzJ0G', 1);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('guest', 'ROLE_GUEST');
