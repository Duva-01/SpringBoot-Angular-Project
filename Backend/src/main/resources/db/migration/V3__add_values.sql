-- V3__add_values.sql
USE elcademy_backend;

/*
Exercise 2:

Create a DML sql script that inserts (at least) 2 different genders, 4 different job positions, and 1 user with at least 1 address.
All the fields should be inserted in the database. This sql script file is one of the deliverables of this practice.
*/

-- Insert 2 genders
INSERT INTO gender (name) VALUES ('Male');
INSERT INTO gender (name) VALUES ('Female');

-- Insert 4 job positions
INSERT INTO job_position (name) VALUES ('Software Engineer');
INSERT INTO job_position (name) VALUES ('Data Scientist');
INSERT INTO job_position (name) VALUES ('Product Manager');
INSERT INTO job_position (name) VALUES ('UX Designer');
INSERT INTO job_position (name) VALUES ('Devops');

-- Insert 1 user
INSERT INTO user (
    username, password, creation_datetime, first_name, first_surname, second_surname, birthdate,
    time_of_breakfast, gender_id, job_position_id
) VALUES (
             'duva01', '1234', NOW(), 'David', 'Martinez', 'Diaz', '2001-07-08',
             '08:00:00', 1, 1
         );

-- Insert 1 address
INSERT INTO address (
    street_name, street_number, user_id, main_address
) VALUES (
             'Recogidas', 123, 1, TRUE
         );

SELECT * FROM gender;

SELECT * FROM job_position;

SELECT * FROM user;

SELECT * FROM address;

SELECT * FROM user_image;
