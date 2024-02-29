CREATE TABLE IF NOT EXISTS Gym_user (
    ID SERIAL PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    user_name varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    is_active boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS Customer (
    ID SERIAL PRIMARY KEY,
    date_of_birth DATE,
    address varchar(255),
    user_id INTEGER REFERENCES Gym_user(ID) UNIQUE
);
