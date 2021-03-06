drop table COMM_MEMBER_EXPERTISE;
drop table COMM_MEMBER_ROLES;
drop table COMM_MEMBERSHIPS;


----------------------
-- COMM_MEMBERSHIPS --
----------------------
-- Table Script
CREATE TABLE COMM_MEMBERSHIPS ( 
    COMM_MEMBERSHIP_ID NUMBER(12,0) NOT NULL,
    COMMITTEE_ID_FK NUMBER(12,0) NOT NULL,
    COMMITTEE_ID VARCHAR2(15) NOT NULL,
    PERSON_ID VARCHAR2(9) NULL,
    ROLODEX_ID NUMBER(12,0) NULL, 
    PERSON_NAME VARCHAR2(90) NOT NULL, 
    MEMBERSHIP_ID VARCHAR2(10) NOT NULL,
    SEQUENCE_NUMBER NUMBER(4,0) NOT NULL,
    PAID_MEMBER_FLAG VARCHAR2(1) NOT NULL,
    TERM_START_DATE DATE NOT NULL,
    TERM_END_DATE DATE,
    MEMBERSHIP_TYPE_CODE VARCHAR2(3) NOT NULL,
    COMMENTS CLOB,
    CONTACT_NOTES CLOB,
    TRAINING_NOTES CLOB,
    UPDATE_TIMESTAMP DATE, 
    UPDATE_USER VARCHAR2(60), 
    VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL, 
    OBJ_ID VARCHAR2(36) DEFAULT SYS_GUID() NOT NULL);

-- Primary Key Constraint 
ALTER TABLE COMM_MEMBERSHIPS 
ADD CONSTRAINT PK_COMM_MEMBERSHIPS 
PRIMARY KEY (COMM_MEMBERSHIP_ID);
 
-- Foreign Key Constraint(s)  
ALTER TABLE COMM_MEMBERSHIPS 
ADD CONSTRAINT FK_COMM_MEMBERSHIPS 
FOREIGN KEY (COMMITTEE_ID_FK) 
REFERENCES COMMITTEE (ID);

ALTER TABLE COMM_MEMBERSHIPS 
ADD CONSTRAINT FK_COMM_MEMBERSHIPS_2 
FOREIGN KEY (MEMBERSHIP_TYPE_CODE) 
REFERENCES COMM_MEMBERSHIP_TYPE (MEMBERSHIP_TYPE_CODE);


-----------------------
-- COMM_MEMBER_ROLES --
-----------------------
-- Table Script
CREATE TABLE COMM_MEMBER_ROLES ( 
    COMM_MEMBER_ROLES_ID NUMBER(12,0) NOT NULL, 
    COMM_MEMBERSHIP_ID_FK NUMBER(12,0) NOT NULL, 
    MEMBERSHIP_ID VARCHAR2(10) NOT NULL, 
    SEQUENCE_NUMBER NUMBER(4,0) NOT NULL, 
    MEMBERSHIP_ROLE_CODE VARCHAR2(3) NOT NULL, 
    START_DATE DATE NOT NULL, 
    END_DATE DATE NOT NULL, 
    UPDATE_TIMESTAMP DATE NOT NULL, 
    UPDATE_USER VARCHAR2(60) NOT NULL, 
    VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL,
    OBJ_ID VARCHAR2(36) DEFAULT SYS_GUID() NOT NULL);
    
-- Primary Key Constraint
ALTER TABLE COMM_MEMBER_ROLES 
ADD CONSTRAINT PK_COMM_MEMBER_ROLES 
PRIMARY KEY (COMM_MEMBER_ROLES_ID);

-- Foreign Key Constraint(s)
ALTER TABLE COMM_MEMBER_ROLES 
ADD CONSTRAINT FK_COMM_MEMBER_ROLES 
FOREIGN KEY (COMM_MEMBERSHIP_ID_FK) 
REFERENCES COMM_MEMBERSHIPS (COMM_MEMBERSHIP_ID); 

ALTER TABLE COMM_MEMBER_ROLES 
ADD CONSTRAINT FK_COMM_MEMBER_ROLES_2 
FOREIGN KEY (MEMBERSHIP_ROLE_CODE) 
REFERENCES MEMBERSHIP_ROLE (MEMBERSHIP_ROLE_CODE); 


---------------------------
-- COMM_MEMBER_EXPERTISE --
---------------------------
-- Table Script
CREATE TABLE COMM_MEMBER_EXPERTISE ( 
    COMM_MEMBER_EXPERTISE_ID NUMBER(12,0) NOT NULL, 
    COMM_MEMBERSHIP_ID_FK NUMBER(12,0) NOT NULL, 
    MEMBERSHIP_ID VARCHAR2(10) NOT NULL, 
    SEQUENCE_NUMBER NUMBER(4,0) NOT NULL, 
    RESEARCH_AREA_CODE VARCHAR2(8) NOT NULL, 
    UPDATE_TIMESTAMP DATE NOT NULL, 
    UPDATE_USER VARCHAR2(60) NOT NULL, 
    VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL, 
    OBJ_ID VARCHAR2(36) DEFAULT SYS_GUID() NOT NULL);

-- Primary Key Constraint 
ALTER TABLE COMM_MEMBER_EXPERTISE 
ADD CONSTRAINT PK_COMM_MEMBER_EXPERTISE 
PRIMARY KEY (COMM_MEMBER_EXPERTISE_ID);

-- Foreign Key Constraint(s)
ALTER TABLE COMM_MEMBER_EXPERTISE 
ADD CONSTRAINT FK_COMM_MEMBER_EXPERTISE 
FOREIGN KEY (COMM_MEMBERSHIP_ID_FK) 
REFERENCES COMM_MEMBERSHIPS (COMM_MEMBERSHIP_ID);

ALTER TABLE COMM_MEMBER_EXPERTISE 
ADD CONSTRAINT FK_COMM_MEMBER_EXPERTISE_2 
FOREIGN KEY (RESEARCH_AREA_CODE) 
REFERENCES RESEARCH_AREAS (RESEARCH_AREA_CODE); 


commit;