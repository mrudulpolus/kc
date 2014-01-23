CREATE TABLE NOTIFICATION_TYPE_RECIPIENT (
  NOTIFICATION_TYPE_RECIPIENT_ID DECIMAL(6,0) NOT NULL,
  NOTIFICATION_TYPE_ID           DECIMAL(6,0) NOT NULL,
  ROLE_ID                        VARCHAR(40) NOT NULL,
  ROLE_QUALIFIER                 VARCHAR(200) NOT NULL,
  TO_OR_CC                       VARCHAR(1) NOT NULL,
  UPDATE_USER                    VARCHAR(60) NOT NULL,
  UPDATE_TIMESTAMP               DATE NOT NULL,
  VER_NBR                        DECIMAL(8,0) DEFAULT 1 NOT NULL, 
  OBJ_ID                         VARCHAR(36) NOT NULL
);

ALTER TABLE NOTIFICATION_TYPE_RECIPIENT
  ADD CONSTRAINT PK_VALID_NOTIFICATION_ROLE PRIMARY KEY (NOTIFICATION_TYPE_RECIPIENT_ID);