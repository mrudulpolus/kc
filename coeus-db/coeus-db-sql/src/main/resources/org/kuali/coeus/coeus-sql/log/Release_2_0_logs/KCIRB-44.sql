/* Table Script */
CREATE TABLE PROTOCOL_DOCUMENT(
DOCUMENT_NUMBER NUMBER(10) NOT NULL,
VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL,
OBJ_ID VARCHAR2(36) DEFAULT SYS_GUID()  NOT NULL,
UPDATE_TIMESTAMP DATE NOT NULL,
UPDATE_USER VARCHAR2(10) NOT NULL);

/* Primary Key Constraint */
ALTER TABLE PROTOCOL_DOCUMENT
ADD CONSTRAINT PK_PROTOCOL_DOCUMENT
PRIMARY KEY (DOCUMENT_NUMBER);


insert into protocol_document (DOCUMENT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
select DOCUMENT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER
from protocol;

commit;


/* Foreign Key Constraint(s) */ 
ALTER TABLE PROTOCOL
ADD CONSTRAINT FK_PROTOCOL_DOCUMENT 
FOREIGN KEY (DOCUMENT_NUMBER) 
REFERENCES PROTOCOL_DOCUMENT (DOCUMENT_NUMBER);
