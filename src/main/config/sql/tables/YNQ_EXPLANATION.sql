CREATE TABLE YNQ_EXPLANATION 
   (	"QUESTION_ID" VARCHAR2(4) constraint YNQ_EXPLANATIONN1 NOT NULL ENABLE, 
	"EXPLANATION_TYPE" CHAR(1) constraint YNQ_EXPLANATIONN2 NOT NULL ENABLE, 
	"EXPLANATION" LONG, 
	"UPDATE_TIMESTAMP" DATE constraint YNQ_EXPLANATIONN3 NOT NULL ENABLE, 
	"UPDATE_USER" VARCHAR2(60) constraint YNQ_EXPLANATIONN4 NOT NULL ENABLE, 
	 "VER_NBR" NUMBER(8,0) DEFAULT 1  constraint  YNQ_EXPLANATIONN5  NOT NULL ENABLE,
	"OBJ_ID" VARCHAR2(36) DEFAULT SYS_GUID()  constraint  YNQ_EXPLANATIONN6  NOT NULL ENABLE,
	CONSTRAINT "PK_YNQ_EXPLANATION_KRA" PRIMARY KEY ("QUESTION_ID", "EXPLANATION_TYPE") ENABLE
)
/

