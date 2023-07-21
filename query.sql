-- Accounts insert
INSERT INTO account(email, password, status, role_id) VALUES ('hai@gmail.com', '123456', 'PENDING', 1);

-- Roles insert
INSERT INTO role(name) VALUES("FORMER_STUDENT");
INSERT INTO role(name) VALUES("TEACHER");
INSERT INTO role(name) VALUES("ADMIN");

SELECT * FROM account;