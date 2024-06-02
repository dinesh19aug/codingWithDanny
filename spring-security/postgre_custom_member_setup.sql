-- Enable the citext extension
CREATE EXTENSION IF NOT EXISTS citext;

-- Create the members table

CREATE TABLE members (
                         user_id CITEXT NOT NULL PRIMARY KEY,
                         password CITEXT NOT NULL,
                         active BOOLEAN NOT NULL
);


-- Create the roles table
CREATE TABLE roles ( user_id CITEXT NOT NULL,
                     role CITEXT NOT NULL,
                     CONSTRAINT fk_authorities_users
                     FOREIGN KEY (user_id)
                     REFERENCES members(user_id)
);

-- Insert into members table
INSERT INTO members (user_id, password, active) VALUES ('mary', '$2a$12$sCaiyQkmCGTA9EwvcmlRCOoD6yVaDkLstfzig5mE0zAWySYLk0giS', true);
INSERT INTO members (user_id, password, active) VALUES ('john', '$2a$12$B4S/ZmPKUxitIz94RqtM6eP7rfXcUewnnACUKkkdp4RVmnqdbXC2G', true);
INSERT INTO members (user_id, password, active) VALUES ('larry', '$2a$12$vfN3.fN.KP3Xja2kEDF6bu52cxZJ8eDGF2MRe1LsZl2/Qtm8uYkdy', true);

-- Insert into roles table for mary
INSERT INTO roles (user_id, role) VALUES ('mary', 'ROLE_EMPLOYEE');
INSERT INTO roles (user_id, role) VALUES ('mary', 'ROLE_MANAGER');
INSERT INTO roles (user_id, role) VALUES ('mary', 'ROLE_ADMIN');

-- Insert into roles table for john
INSERT INTO roles (user_id, role) VALUES ('john', 'ROLE_EMPLOYEE');

-- Insert into roles table for larry
INSERT INTO roles (user_id, role) VALUES ('larry', 'ROLE_EMPLOYEE');
INSERT INTO roles (user_id, role) VALUES ('larry', 'ROLE_MANAGER');