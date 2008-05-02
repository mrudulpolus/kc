 CREATE TABLE VALID_CE_RATE_TYPES 
   (	"COST_ELEMENT" VARCHAR2(8) constraint VALID_CE_RATE_TYPESN1 NOT NULL ENABLE, 
	"RATE_CLASS_CODE" NUMBER(3,0) constraint VALID_CE_RATE_TYPESN2 NOT NULL ENABLE, 
	"RATE_TYPE_CODE" NUMBER(3,0) constraint VALID_CE_RATE_TYPESN3 NOT NULL ENABLE, 
	"UPDATE_TIMESTAMP" DATE constraint VALID_CE_RATE_TYPESN4 NOT NULL ENABLE, 
	"UPDATE_USER" VARCHAR2(60) constraint VALID_CE_RATE_TYPESN5 NOT NULL ENABLE, 
	 "VER_NBR" NUMBER(8,0) DEFAULT 1  constraint  VALID_CE_RATE_TYPESN6  NOT NULL ENABLE,
	"OBJ_ID" VARCHAR2(36) DEFAULT SYS_GUID()  constraint  VALID_CE_RATE_TYPESN7  NOT NULL ENABLE,
	CONSTRAINT "PK_VALID_CE_RATE_TYPES_KRA" PRIMARY KEY ("COST_ELEMENT", "RATE_CLASS_CODE", "RATE_TYPE_CODE") ENABLE
) ;

ALTER table VALID_CE_RATE_TYPES modify RATE_CLASS_CODE VARCHAR2(3);
ALTER table VALID_CE_RATE_TYPES modify RATE_TYPE_CODE VARCHAR2(3);

ALTER TABLE VALID_CE_RATE_TYPES ADD (CONSTRAINT "FK_VALID_CE_RATE_TYPES_KRA" FOREIGN KEY ("RATE_CLASS_CODE", "RATE_TYPE_CODE")
	  REFERENCES "RATE_TYPE" ("RATE_CLASS_CODE", "RATE_TYPE_CODE") ) ;
 

 ALTER TABLE VALID_CE_RATE_TYPES ADD (CONSTRAINT "FK_VALID_CE_RATE_TYPES_CE_KRA" FOREIGN KEY ("COST_ELEMENT")
	  REFERENCES "COST_ELEMENT" ("COST_ELEMENT") ) ;

