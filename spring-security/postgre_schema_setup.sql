-- Enable the citext extension
CREATE EXTENSION IF NOT EXISTS citext;

-- Create the users table
CREATE TABLE users (
                       username CITEXT NOT NULL PRIMARY KEY,
                       password CITEXT NOT NULL,
                       enabled BOOLEAN NOT NULL
);

-- Create the authorities table
CREATE TABLE authorities (
                             username CITEXT NOT NULL,
                             authority CITEXT NOT NULL,
                             CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);


-- Insert into users table

INSERT INTO users (username, password, enabled) VALUES ('mary', '{noop}mary', true);
INSERT INTO users (username, password, enabled) VALUES ('john', '{noop}john', true);
INSERT INTO users (username, password, enabled) VALUES ('larry', '{noop}larry', true);

-- Insert into authorities table for mary
INSERT INTO authorities (username, authority) VALUES ('mary', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('mary', 'ROLE_MANAGER');
INSERT INTO authorities (username, authority) VALUES ('mary', 'ROLE_ADMIN');

-- Insert into authorities table for john
INSERT INTO authorities (username, authority) VALUES ('john', 'ROLE_EMPLOYEE');

-- Insert into authorities table for larry
INSERT INTO authorities (username, authority) VALUES ('larry', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('larry', 'ROLE_MANAGER');