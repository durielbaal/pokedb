CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE IF NOT EXISTS Pokemon (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pokedex_number VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);

