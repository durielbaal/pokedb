-- Create the User_roles table
CREATE TABLE IF NOT EXISTS User_roles (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES "users"(username)
);