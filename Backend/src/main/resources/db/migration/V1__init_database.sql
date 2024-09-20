CREATE SCHEMA  IF NOT EXISTS elcademy_backend;

USE elcademy_backend;

/*
Exercise 1:

Create a DDL sql script that creates a database schema called "elcademy_backend" and also creates the tables that represent 
the entities listed before. This sql script file is one of the deliverables of this practice.
*/

-- Create Gender table
CREATE TABLE IF NOT EXISTS gender (
  `id` INT AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`)
);

-- Create JobPosition table
CREATE TABLE IF NOT EXISTS job_position (
  `id` INT AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`)
);

-- Create Users table
CREATE TABLE IF NOT EXISTS user (
  `id` INT AUTO_INCREMENT,
  `username` VARCHAR(150) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `creation_datetime` DATETIME NOT NULL,
  `first_name` VARCHAR(150) NOT NULL,
  `first_surname` VARCHAR(150) NOT NULL,
  `second_surname` VARCHAR(150) NULL,
  `birthdate` DATE NOT NULL,
  `time_of_breakfast` TIME NULL,
  `gender_id` INT NOT NULL,
  `job_position_id` INT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`gender_id`) REFERENCES gender(`id`),
  FOREIGN KEY (`job_position_id`) REFERENCES job_position(`id`)
);

-- Create Address table
CREATE TABLE IF NOT EXISTS address (
  `id` INT AUTO_INCREMENT,
  `street_name` VARCHAR(255) NOT NULL,
  `street_number` INT NULL,
  `user_id` INT NOT NULL,
  `main_address` BOOLEAN NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`)
);

-- Create UserImage table
CREATE TABLE IF NOT EXISTS user_image (
    `id` INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `image` LONGTEXT NOT NULL,
    `user_id` INT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES user(`id`)
);