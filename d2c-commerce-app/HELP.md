# Read Me First

This project is part of FF4J feature flag series

# Getting Started

* To start the project download and just use one of the following ways 
  * Run from IDE by Running the D2CCommerceAp.Java class as Springboot application
    * `mvn spring-boot:run` 
      * First run `mvn clean install`, then run `java -jar d2c-commerce-app-0.0.1-SNAPSHOT.jar`
        * To execute the database - Run the docker compose file.
          * To access the db --> Connect to db - ff4jdb and pass username and password as danny/danny
          * Create a new db `bookstore` and add the `users` table
            ```sql
            drop table users;
            CREATE DATABASE "bookstore";
            CREATE TABLE IF NOT EXISTS users (
                user_id serial ,
                username varchar(65) NOT NULL,
                password varchar(255) NOT NULL,
                role varchar(45) NOT NULL,
                enabled boolean DEFAULT NULL,
                PRIMARY KEY (user_id)
            );
    
            INSERT INTO users(username,password,role,enabled) VALUES ('user','$2a$10$2J1ohBZL0pjFR4z9mDIZ7./uJkJWm8z85F3OIYbehwZ/13qpeksdC','ROLE_USER', true);
    
            INSERT INTO users(username,password,role,enabled) VALUES ('admin','$2a$10$DyOrchWc1jTGMDVc5feS5Oi6Vt9sqco7RaOI6rO.aJ6wco.RFc6nq','ROLE_ADMIN', true);

        ```