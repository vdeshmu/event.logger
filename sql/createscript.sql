DROP TABLE IF EXISTS EVENTS;

CREATE TABLE IF NOT EXISTS
EVENTS(ID VARCHAR(40) NOT NULL,
DURATION INT DEFAULT 0,
HOST VARCHAR(40),
TYPE VARCHAR(40),
ALERT BOOLEAN DEFAULT FALSE NOT NULL);