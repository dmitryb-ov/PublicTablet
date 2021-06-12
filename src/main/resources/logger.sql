CREATE TABLE logs
(
    dated   TIMESTAMP,
    logger  VARCHAR(100) NOT NULL,
    level   VARCHAR(15)  NOT NULL,
    message VARCHAR(2000)
);