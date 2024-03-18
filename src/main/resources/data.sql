-- Insert default admin user if not exists
INSERT INTO users (username, password, email, first_name, last_name, date_of_birth, role, created_at, updated_at)
SELECT 'admin', 'admin123', 'admin@example.com', 'Admin', 'User', '1990-01-01', 'ADMIN', '1990-01-01', '1990-01-01'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

-- Generating dummy users, passswords are "user123" encrypted
INSERT INTO users (username, password, email, first_name, last_name, date_of_birth, role, created_at, updated_at)
SELECT 'alphaOmega27', '$2a$10$fWnxfvNBKNjiJL3mEAnftuW5XuNSmedhbAL5HSpF3QEnX.WvdWo8G', 'elo.lopez@example.com', 'Eleanor', 'Lopez', '1993-07-12', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'alphaOmega27');

INSERT INTO users (username, password, email, first_name, last_name, date_of_birth, role, created_at, updated_at)
SELECT 'stormRider', '$2a$10$fWnxfvNBKNjiJL3mEAnftuW5XuNSmedhbAL5HSpF3QEnX.WvdWo8G', 'liam.garcia@example.com', 'Liam', 'Garcia', '1988-05-25', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'stormRider');

INSERT INTO users (username, password, email, first_name, last_name, date_of_birth, role, created_at, updated_at)
SELECT 'sapphire22', '$2a$10$fWnxfvNBKNjiJL3mEAnftuW5XuNSmedhbAL5HSpF3QEnX.WvdWo8G', 'sophiaw@example.com', 'Sophia', 'Walker', '1979-11-30', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'sapphire22');

INSERT INTO users (username, password, email, first_name, last_name, date_of_birth, role, created_at, updated_at)
SELECT 'blazeRunner', '$2a$10$fWnxfvNBKNjiJL3mEAnftuW5XuNSmedhbAL5HSpF3QEnX.WvdWo8G', 'isaacperez@example.com', 'Isaac', 'Perez', '1995-03-18', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'blazeRunner');

INSERT INTO users (username, password, email, first_name, last_name, date_of_birth, role, created_at, updated_at)
SELECT 'echoXray', '$2a$10$fWnxfvNBKNjiJL3mEAnftuW5XuNSmedhbAL5HSpF3QEnX.WvdWo8G', 'avasmith@example.com', 'Ava', 'Smith', '1983-09-05', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'echoXray');

INSERT INTO users (username, password, email, first_name, last_name, date_of_birth, role, created_at, updated_at)
SELECT 'phoenixFlame', '$2a$10$fWnxfvNBKNjiJL3mEAnftuW5XuNSmedhbAL5HSpF3QEnX.WvdWo8G', 'm.taylor@example.com', 'Mia', 'Taylor', '1976-02-21', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'phoenixFlame');



-- Generating dummy accounts
INSERT INTO accounts (balance, account_number, user_id)
SELECT 51235, 123456, 2
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 123456);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 78965, 987654, 3
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 987654);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 23457, 234567, 4
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 234567);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 87654, 345678, 5
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 345678);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 45679, 456789, 6
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 456789);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 12346, 567890, 7
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 567890);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 67890, 678901, 2
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 678901);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 98765, 789012, 3
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 789012);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 23457, 890123, 4
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 890123);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 87654, 901234, 5
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 901234);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 34568, 901235, 6
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 901235);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 65432, 901236, 7
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 901236);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 12346, 901237, 2
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 901237);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 54322, 901238, 3
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 901238);

INSERT INTO accounts (balance, account_number, user_id)
SELECT 87654, 901239, 4
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE account_number = 901239);




