SET search_path TO public;
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE IF NOT EXISTS Pokemon (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pokedex_number VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);
-- Create the User table
CREATE TABLE IF NOT EXISTS Users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


