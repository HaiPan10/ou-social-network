DROP DATABASE IF EXISTS `ou-social-network`;
CREATE DATABASE `ou-social-network`;
USE `ou-social-network`;

DROP TABLE IF EXISTS `ou-social-network`.`role`;
CREATE TABLE `role` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    
    PRIMARY KEY (id)
    
)   ENGINE=INNODB;

DROP TABLE IF EXISTS `ou-social-network`.`account`;
CREATE TABLE `account` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    email VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(300) NOT NULL,
    created_date DATETIME,
    verification_code VARCHAR(64),
    status ENUM('LOCKED',
				'ACTIVE', 
				'AUTHENTICATION_PENDING',
                'EMAIL_VERIFICATION_PENDING',
                'REJECT',
                'PASSWORD_CHANGE_REQUIRED') DEFAULT 'ACTIVE',
    role_id INT UNSIGNED NOT NULL,
    
    PRIMARY KEY (id),
    INDEX (role_id),
    FOREIGN KEY (role_id)
        REFERENCES role(id)
        ON DELETE CASCADE
    
)   ENGINE=INNODB;

DROP TABLE IF EXISTS `ou-social-network`.`user`;
CREATE TABLE `user` (
    id INT UNSIGNED NOT NULL,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    dob DATETIME,
    avatar VARCHAR(300),
    cover_avatar VARCHAR(300),

    PRIMARY KEY (id),
    FOREIGN KEY (id)
		REFERENCES account(id)
        ON DELETE CASCADE
    
)   ENGINE=INNODB;

DROP TABLE IF EXISTS `ou-social-network`.`user_student`;
CREATE TABLE `user_student` (
    id INT UNSIGNED NOT NULL,
    student_identical VARCHAR(10) NOT NULL UNIQUE,

    PRIMARY KEY (id),
    FOREIGN KEY (id)
		REFERENCES user(id)
        ON DELETE CASCADE
    
)   ENGINE=INNODB;

DROP TABLE IF EXISTS `ou-social-network`.`post`;
CREATE TABLE `post` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    content VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    is_active_comment BOOLEAN DEFAULT true,
    user_id INT UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    INDEX (user_id),
    FOREIGN KEY (user_id)
		REFERENCES user(id)
        ON DELETE CASCADE
    
)   ENGINE=INNODB;

DROP TABLE IF EXISTS `ou-social-network`.`image_in_post`;
CREATE TABLE `image_in_post` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    image_url VARCHAR(300),
    created_at DATETIME,
    updated_at DATETIME,
    post_id INT UNSIGNED NOT NULL,

    PRIMARY KEY (id),
	INDEX (post_id),
    FOREIGN KEY (post_id)
		REFERENCES post(id)
        ON DELETE CASCADE
    
)   ENGINE=INNODB;

DROP TABLE IF EXISTS `ou-social-network`.`reaction`;
CREATE TABLE `reaction` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,

    PRIMARY KEY (id)
    
)   ENGINE=INNODB;

DROP TABLE IF EXISTS `ou-social-network`.`comment`;
CREATE TABLE `comment` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    content VARCHAR(255) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    post_id INT UNSIGNED NOT NULL,
    user_id INT UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    INDEX (post_id),
    INDEX (user_id),
    FOREIGN KEY (post_id)
		REFERENCES post(id)
        ON DELETE CASCADE,
    FOREIGN KEY (user_id)
		REFERENCES user(id)
        ON DELETE CASCADE
    
)   ENGINE=INNODB;

DROP TABLE IF EXISTS `ou-social-network`.`post_reaction`;
CREATE TABLE `post_reaction` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    created_at DATETIME,
    post_id INT UNSIGNED NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    reaction_id INT UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    INDEX (post_id),
    INDEX (user_id),
    INDEX (reaction_id),
    FOREIGN KEY (post_id)
		REFERENCES post(id)
        ON DELETE CASCADE,
    FOREIGN KEY (user_id)
		REFERENCES user(id)
        ON DELETE CASCADE,
    FOREIGN KEY (reaction_id)
		REFERENCES reaction(id)
        ON DELETE CASCADE
    
)   ENGINE=INNODB;

-- DROP TABLE IF EXISTS `ou-social-network`.`comment_reaction`;
-- CREATE TABLE `comment_reaction` (
--     id INT UNSIGNED NOT NULL AUTO_INCREMENT,
--     created_at DATETIME,
--     comment_id INT UNSIGNED NOT NULL,
--     user_id INT UNSIGNED NOT NULL,
--     reaction_id INT UNSIGNED NOT NULL,

--     PRIMARY KEY (id),
--     INDEX (comment_id),
--     INDEX (user_id),
--     INDEX (reaction_id),
--     FOREIGN KEY (comment_id)
-- 		REFERENCES comment(id)
--         ON DELETE CASCADE,
--     FOREIGN KEY (user_id)
-- 		REFERENCES user(id)
--         ON DELETE CASCADE,
--     FOREIGN KEY (reaction_id)
-- 		REFERENCES reaction(id)
--         ON DELETE CASCADE
    
-- )   ENGINE=INNODB;

-- INSERT default rows after created
INSERT INTO role(name) VALUES("ROLE_FORMER_STUDENT");
INSERT INTO role(name) VALUES("ROLE_TEACHER");
INSERT INTO role(name) VALUES("ROLE_ADMIN");

-- role_id 3 is the ADMIN role
-- password is 123456
INSERT INTO account(email, password, created_date, role_id)
VALUES ('admin456@gmail.com',
'$2a$10$3YohAzphxM8cU1uvEkikleeAf4xPlPZrQ0eqx5iPgc0bcUT48j/SC', now(), '3');

INSERT INTO user(id, first_name, last_name, dob, avatar)
VALUES (1, 'Hai', 'Phan Thanh', '2002-05-18',
'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1669639320/aukutc1ioxrflkionglc.png');

INSERT INTO reaction(name) VALUES ('Thích');
INSERT INTO reaction(name) VALUES ('Haha');
INSERT INTO reaction(name) VALUES ('Tim');