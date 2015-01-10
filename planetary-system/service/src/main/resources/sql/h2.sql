DROP TABLE IF EXISTS PLANETS CASCADE;

CREATE TABLE PLANETS
(
  ID              NUMBER PRIMARY KEY NOT NULL,
  NAME            VARCHAR2(30 CHAR) NOT NULL,
  RADIUS          DECIMAL(10,2) NOT NULL,
  CIRCUMFERENCE   DECIMAL(10,2) NOT NULL,
  AREA            DECIMAL(15,2) NOT NULL,
  VOLUME          DECIMAL(30,2) NOT NULL,
  MASS            DECIMAL(30,2) NOT NULL
);

CREATE UNIQUE INDEX UI_PLANETS ON PLANETS (NAME);

INSERT INTO PLANETS
SELECT * FROM CSVREAD('classpath:csv/planets.csv');