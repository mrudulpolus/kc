CREATE TABLE AWARD_TEMPLATE_TERMS (
    AWARD_TEMPLATE_TERMS_ID NUMBER (12, 0) NOT NULL, 
    VER_NBR NUMBER (8, 0) DEFAULT 1 NOT NULL, 
    OBJ_ID VARCHAR2 (36) DEFAULT SYS_GUID () NOT NULL, 
    AWARD_TEMPLATE_CODE NUMBER (5, 0) NOT NULL, 
    SPONSOR_TERM_ID NUMBER (12, 0) NOT NULL, 
    UPDATE_TIMESTAMP DATE NOT NULL, 
    UPDATE_USER VARCHAR2 (60) NOT NULL) ;

ALTER TABLE AWARD_TEMPLATE_TERMS 
    ADD CONSTRAINT PK_AWARD_TEMPLATE_TERMS 
            PRIMARY KEY (AWARD_TEMPLATE_TERMS_ID) ;