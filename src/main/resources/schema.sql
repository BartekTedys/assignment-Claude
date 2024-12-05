-- Create Users Table
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       enabled BOOLEAN DEFAULT TRUE
);

-- Create User Roles Table
CREATE TABLE user_roles (
                            user_id BIGINT,
                            role VARCHAR(50),
                            FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create Households Table
CREATE TABLE households (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            address VARCHAR(500)
);

-- Create Pets Table
CREATE TABLE pets (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      type VARCHAR(50) NOT NULL,
                      birth_date VARCHAR(100),
                      household_id BIGINT,
                      FOREIGN KEY (household_id) REFERENCES households(id)
);