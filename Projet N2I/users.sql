DROP DATABASE IF EXISTS crtv_registration;
CREATE DATABASE crtv_registration;
USE crtv_registration;

CREATE TABLE Users (
  id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username varchar(100) NOT NULL,
  password varchar(100) NOT NULL
) 
ENGINE=InnoDB;

INSERT into Users
VALUES (NULL, "admin", sha2("admin",256));