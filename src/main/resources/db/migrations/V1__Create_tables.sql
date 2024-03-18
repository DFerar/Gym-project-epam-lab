CREATE TABLE IF NOT EXISTS Gym_user
(
    ID         BIGINT PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL,
    user_name  varchar(255) NOT NULL UNIQUE,
    password   varchar(255) NOT NULL,
    is_active  boolean      NOT NULL
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

CREATE SEQUENCE IF NOT EXISTS customer_SEQ;
CREATE SEQUENCE IF NOT EXISTS instructor_SEQ;
CREATE SEQUENCE IF NOT EXISTS training_SEQ;
CREATE SEQUENCE IF NOT EXISTS gym_user_SEQ;
CREATE SEQUENCE IF NOT EXISTS training_type_SEQ;

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
