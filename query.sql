-- Accounts insert
INSERT INTO account(email, password, status, role_id) VALUES ('hai@gmail.com', '123456', 'PENDING', 1);

-- Roles insert
INSERT INTO role(name) VALUES("ROLE_FORMER_STUDENT");
INSERT INTO role(name) VALUES("ROLE_TEACHER");
INSERT INTO role(name) VALUES("ROLE_ADMIN");

SELECT * FROM account;
SELECT * FROM user;
SELECT * FROM user_student;
SELECT * FROM role;

UPDATE role SET name = 'ROLE_ADMIN' where id = 3;
UPDATE role SET name = 'ROLE_TEACHER' where id = 2;
UPDATE role SET name = 'ROLE_FORMER_STUDENT' where id = 1;

INSERT INTO account(email, password, role_id) VALUES('hai123@gmail.com', '123456', 1);
INSERT INTO user(id, first_name, last_name) VALUES (30, 'Hai', 'Phan Thanh');
INSERT INTO user_student(id, student_identical) VALUES(30, '2051010083');