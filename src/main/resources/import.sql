INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO `brogger`.`user` (`id`, `avatar`, `creat_dt`, `email`, `modify_dt`, `name`, `password`, `remark`, `username`) VALUES ('1', NULL, NULL, 'xioabai@sina.com', NULL, '小白先生', '$2a$10$SaxQS12Q4XJOxsuglgpNwe2fu1g4krhuta.5djq3lvnG5KrhKk8Ba', NULL, 'admin');
INSERT INTO `brogger`.`user` (`id`, `avatar`, `creat_dt`, `email`, `modify_dt`, `name`, `password`, `remark`, `username`) VALUES ('2', NULL, NULL, 'sucl@sina.com', NULL, 'sucl', '$2a$10$TywUd/G3J5PMbsA60TnQBOiwa5aEcZ5Ol9FKN5hc/E3iSQJMoBlIK', NULL, 'sucl');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);

UPDATE `brogger`.`hibernate_sequence` SET `next_val`='3' WHERE 1=1 LIMIT 1;