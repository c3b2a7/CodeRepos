INSERT INTO `oauth_client_details`(`client_id`, `resource_ids`, `client_secret`, `scope`,
                                   `authorized_grant_types`, `web_server_redirect_uri`,
                                   `authorities`, `access_token_validity`,
                                   `refresh_token_validity`, `additional_information`,
                                   `autoapprove`)
VALUES ('gateway', '',
        '{sha256}12425bfe64aff82ba9c2f7d1464fde5454bd8d309b9a41180468e0dcfd61aa352f4f0dd4aa3e8d22',
        'all', 'authorization_code,password,refresh_token',
        'http://localhost:8080/login/oauth2/code/auth-server', '', NULL, NULL, '{}',
        'all');
INSERT INTO `oauth_client_details`(`client_id`, `resource_ids`, `client_secret`, `scope`,
                                   `authorized_grant_types`, `web_server_redirect_uri`,
                                   `authorities`, `access_token_validity`,
                                   `refresh_token_validity`, `additional_information`,
                                   `autoapprove`)
VALUES ('resource', '',
        '{sha256}251db28971a7bfc6ee6815ea61c4baca176d5336c358d7d4281a324c9893eec8d018dcc46abc323c',
        '', '', '', '', NULL, NULL, '{}', '');
INSERT INTO `oauth_client_details`(`client_id`, `resource_ids`, `client_secret`, `scope`,
                                   `authorized_grant_types`, `web_server_redirect_uri`,
                                   `authorities`, `access_token_validity`,
                                   `refresh_token_validity`, `additional_information`,
                                   `autoapprove`)
VALUES ('oauth-client', 'user_info',
        '{sha256}10e50cec2f66758747a24eb9d48ac42f815c909482ca2f80b0ef5bf8d6917ef27e297f959665460a',
        'user_info.read', 'authorization_code,refresh_token,client_credentials,password',
        'http://localhost:8000/authorized', '', NULL, NULL, '{}', NULL);
