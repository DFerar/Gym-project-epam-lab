CREATE TABLE IF NOT EXISTS Gym_user
(
    ID               BIGINT PRIMARY KEY,
    first_name       varchar(255) NOT NULL,
    last_name        varchar(255) NOT NULL,
    user_name        varchar(255) NOT NULL UNIQUE,
    password         varchar(255) NOT NULL,
    is_active        boolean      NOT NULL,
    user_index       int,
    time_of_blocking timestamp
);

CREATE TABLE IF NOT EXISTS Customer
(
    ID            BIGINT PRIMARY KEY,
    date_of_birth DATE,
    address       varchar(255),
    user_id       BIGINT REFERENCES Gym_user (ID) UNIQUE
);

CREATE TABLE IF NOT EXISTS Training_type
(
    ID                 BIGINT PRIMARY KEY,
    training_type_name varchar(225) NOT NULL
);

CREATE TABLE IF NOT EXISTS Instructor
(
    ID             BIGINT PRIMARY KEY,
    specialization BIGINT REFERENCES Training_type (ID),
    user_id        BIGINT REFERENCES Gym_user (ID)
);

CREATE TABLE IF NOT EXISTS Training
(
    ID                BIGINT PRIMARY KEY,
    customer_id       BIGINT REFERENCES Customer (ID),
    instructor_id     BIGINT REFERENCES Instructor (ID),
    training_name     varchar(225) NOT NULL,
    training_type_id  BIGINT REFERENCES Training_type (ID),
    training_date     date         NOT NULL,
    training_duration integer      NOT NULL
);

CREATE TABLE IF NOT EXISTS Customer_instructor
(
    customer_id   BIGINT,
    instructor_id BIGINT,
    PRIMARY KEY (customer_id, instructor_id)
);

CREATE TABLE IF NOT EXISTS token
(
    ID       BIGINT PRIMARY KEY,
    token    VARCHAR UNIQUE,
    username VARCHAR,
    is_valid BOOLEAN
);

CREATE SEQUENCE IF NOT EXISTS customer_SEQ;
CREATE SEQUENCE IF NOT EXISTS instructor_SEQ;
CREATE SEQUENCE IF NOT EXISTS training_SEQ;
CREATE SEQUENCE IF NOT EXISTS gym_user_SEQ;
CREATE SEQUENCE IF NOT EXISTS training_type_SEQ;
CREATE SEQUENCE IF NOT EXISTS tokens_seq;

INSERT INTO training_type
VALUES (1, 'CARDIO'),
       (2, 'TRX'),
       (3, 'CYCLE'),
       (4, 'KEK'),
       (5, 'BOX'),
       (6, 'ABS');
