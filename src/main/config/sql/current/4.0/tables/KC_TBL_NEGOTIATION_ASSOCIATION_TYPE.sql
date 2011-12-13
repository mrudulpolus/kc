CREATE TABLE NEGOTIATION_ASSOCIATION_TYPE  ( 
	"NEGOTIATION_ASSC_TYPE_ID"	NUMBER(22) NOT NULL,
	"NEGOTIATION_ASSC_TYPE_CODE"	VARCHAR2(3) NOT NULL,
	"DESCRIPTION"          	VARCHAR2(30) NOT NULL,
	"UPDATE_TIMESTAMP"     	DATE NOT NULL,
	"UPDATE_USER"          	VARCHAR2(60) NOT NULL,
	"VER_NBR"              	NUMBER(8,0) NOT NULL,
	"OBJ_ID"               	VARCHAR2(36) NOT NULL,
        "ACTV_IND"		VARCHAR2(1) NOT NULL,
	CONSTRAINT "NEGOTIATION_ASSC_TYPE_PK1" PRIMARY KEY("NEGOTIATION_ASSC_TYPE_ID")
)
/
