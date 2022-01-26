/**
 * Author:  anth
 * Created: 28-ene-2021
 */
CREATE USER flightsusr@localhost IDENTIFIED by 'flightspwd';
CREATE DATABASE flightsdb
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;
GRANT SELECT, INSERT, UPDATE, DELETE ON flightsdb.* TO flightsusr@localhost;
USE flightsdb;
CREATE TABLE flights (
    id INT(4) NOT NULL AUTO_INCREMENT,
    code VARCHAR(10) NOT NULL UNIQUE,
    capacity INT(2) DEFAULT 0,
    date DATE NOT NULL,
    time TIME NOT NULL,
    PRIMARY KEY(id)
);
CREATE TABLE passengers (
    id INT(4) NOT NULL AUTO_INCREMENT,
    phone VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(10) NOT NULL,
    minor BOOLEAN DEFAULT true,
    PRIMARY KEY(id)
);
CREATE TABLE flightspassengers (
    flight_id INT(4) NOT NULL,
    passenger_id INT(4) NOT NULL,
    PRIMARY KEY(flight_id, passenger_id)
);
ALTER TABLE flightspassengers ADD FOREIGN KEY fk_flight(flight_id) REFERENCES flights(id);
ALTER TABLE flightspassengers ADD FOREIGN KEY fk_passenger(passenger_id) REFERENCES passengers(id);
INSERT INTO `flights` (`id`, `code`, `capacity`, `date`, `time`) 
VALUES
(1, 'flight01', 101, '2021-01-29', '12:00:00'),
(2, 'flight02', 102, '2021-01-30', '12:00:00'),
(3, 'flight03', 103, '2021-01-31', '12:00:00'),
(4, 'flight04', 104, '2021-02-01', '12:00:00'),
(5, 'flight05', 105, '2021-02-02', '12:00:00'),
(6, 'flight06', 106, '2021-02-03', '12:00:00'),
(7, 'flight07', 107, '2021-02-04', '12:00:00'),
(8, 'flight08', 108, '2021-02-05', '12:00:00');
INSERT INTO `passengers` (`id`, `phone`, `name`, `minor`) 
VALUES
(1, 1234567891, 'pass01', true),
(2, 1234567892, 'pass02', false),
(3, 1234567893, 'pass03', true),
(4, 1234567894, 'pass04', false),
(5, 1234567895, 'pass05', true),
(6, 1234567896, 'pass06', false),
(7, 1234567897, 'pass07', true),
(8, 1234567898, 'pass08', false);