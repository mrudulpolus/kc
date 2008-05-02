 CREATE TABLE BUDGET_SUB_AWARDS 
   (	"PROPOSAL_NUMBER" VARCHAR2(12) constraint BUDGET_SUB_AWARDSN1 NOT NULL ENABLE, 
	"VERSION_NUMBER" NUMBER(3,0) constraint BUDGET_SUB_AWARDSN2 NOT NULL ENABLE, 
	"SUB_AWARD_NUMBER" NUMBER(3,0) constraint BUDGET_SUB_AWARDSN3 NOT NULL ENABLE, 
	"ORGANIZATION_NAME" VARCHAR2(60) constraint BUDGET_SUB_AWARDSN4 NOT NULL ENABLE, 
	"SUB_AWARD_STATUS_CODE" NUMBER(3,0) constraint BUDGET_SUB_AWARDSN5 NOT NULL ENABLE, 
	"SUB_AWARD_XFD_FILE" BLOB DEFAULT empty_blob(), 
	"SUB_AWARD_XFD_FILE_NAME" VARCHAR2(256) constraint BUDGET_SUB_AWARDSN6 NOT NULL ENABLE, 
	"COMMENTS" VARCHAR2(2000), 
	"XFD_UPDATE_USER" VARCHAR2(8), 
	"XFD_UPDATE_TIMESTAMP" DATE, 
	"SUB_AWARD_XML_FILE" CLOB DEFAULT empty_clob(), 
	"TRANSLATION_COMMENTS" VARCHAR2(2000), 
	"XML_UPDATE_USER" VARCHAR2(8), 
	"XML_UPDATE_TIMESTAMP" DATE, 
	"UPDATE_TIMESTAMP" DATE constraint BUDGET_SUB_AWARDSN7 NOT NULL ENABLE, 
	"UPDATE_USER" VARCHAR2(60) constraint BUDGET_SUB_AWARDSN8 NOT NULL ENABLE, 
	 "VER_NBR" NUMBER(8,0) DEFAULT 1  constraint  BUDGET_SUB_AWARDSN9  NOT NULL ENABLE,
	"OBJ_ID" VARCHAR2(36) DEFAULT SYS_GUID()  constraint  BUDGET_SUB_AWARDSN10  NOT NULL ENABLE,
	CONSTRAINT "PK_BUDGET_SUB_AWARDS_KRA" PRIMARY KEY ("PROPOSAL_NUMBER", "VERSION_NUMBER", "SUB_AWARD_NUMBER") ENABLE
);
ALTER TABLE BUDGET_SUB_AWARDS ADD (CONSTRAINT "FK1_BUDGET_SUB_AWARDS_KRA" FOREIGN KEY ("PROPOSAL_NUMBER", "VERSION_NUMBER")
	  REFERENCES "BUDGET" ("PROPOSAL_NUMBER", "VERSION_NUMBER") );
	  
