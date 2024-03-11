CREATE TABLE IF NOT EXISTS Gym_user
(
    ID         SERIAL PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL,
    user_name  varchar(255) NOT NULL UNIQUE,
    password   varchar(255) NOT NULL,
    is_active  boolean      NOT NULL
);

CREATE TABLE IF NOT EXISTS Customer
(
    ID            SERIAL PRIMARY KEY,
    date_of_birth DATE,
    address       varchar(255),
    user_id       INTEGER REFERENCES Gym_user (ID) UNIQUE
);

CREATE TABLE IF NOT EXISTS Training_type
(
    ID                 SERIAL PRIMARY KEY,
    training_type_name varchar(225) NOT NULL
);

CREATE TABLE IF NOT EXISTS Instructor
(
    ID             SERIAL PRIMARY KEY,
    specialization integer REFERENCES Training_type (ID),
    user_id        integer REFERENCES Gym_user (ID)
);

CREATE TABLE IF NOT EXISTS Training
(
    ID                SERIAL PRIMARY KEY,
    customer_id       INTEGER REFERENCES Customer (ID),
    instructor_id     INTEGER REFERENCES Instructor (ID),
    training_name     varchar(225) NOT NULL,
    training_type_id  INTEGER REFERENCES Training_type (ID),
    training_date     date         NOT NULL,
    training_duration integer      NOT NULL
);

CREATE TABLE IF NOT EXISTS Customer_instructor
(
    customer_id   INTEGER,
    instructor_id INTEGER,
    PRIMARY KEY (customer_id, instructor_id)
);

INSERT INTO training_type
VALUES (1, 'CARDIO'),
       (2, 'TRX'),
       (3, 'CYCLE'),
       (4, 'KEK'),
       (5, 'BOX'),
       (6, 'ABS');
INSERT INTO gym_user
VALUES (0, 'admin', 'admin', 'admin.admin', 'admin', true);
INSERT INTO instructor
VALUES (0, 1, 0);
INSERT INTO customer
VALUES (0, '1997-01-01', 'admin', 0)
