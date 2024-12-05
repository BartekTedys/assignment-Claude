-- Insert mock users
INSERT INTO users (username, password, enabled) VALUES
                                                    ('user', '$2a$10$nXHPT8icb1b7dmP2hFEJwe6AwkiFp8xNLapvAf3GyCZ8EdaVye7ki', TRUE),
                                                    ('admin', '$2a$10$nXHPT8icb1b7dmP2hFEJwe6AwkiFp8xNLapvAf3GyCZ8EdaVye7ki', TRUE);

-- Insert user roles
INSERT INTO user_roles (user_id, role) VALUES
                                           (1, 'ROLE_USER'),
                                           (2, 'ROLE_ADMIN');

-- Insert mock households
INSERT INTO households (name, address) VALUES
                                           ('Smith Family Home', '123 Main St, Anytown, USA'),
                                           ('Johnson Residence', '456 Oak Ave, Somewhere, USA');

-- Insert mock pets
INSERT INTO pets (name, type, birth_date, household_id) VALUES
                                                            ('Buddy', 'DOG', '2020-01-15', 1),
                                                            ('Whiskers', 'CAT', '2019-05-20', 1),
                                                            ('Polly', 'BIRD', '2021-03-10', 2);