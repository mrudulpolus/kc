--DROP TABLE EPS_PROP_IDC_RATE;

CREATE TABLE EPS_PROP_IDC_RATE (
 	"PROPOSAL_NUMBER" VARCHAR2(12) constraint EPS_PROP_IDC_RATE_PN NOT NULL ENABLE, 
	"BUDGET_VERSION_NUMBER" NUMBER(3,0) constraint EPS_PROP_IDC_RATE_BV NOT NULL ENABLE,
	"UNRECOVERED_FNA_ID" NUMBER(5,0) constraint EPS_PROP_IDC_RATE_ID NOT NULL ENABLE,
	 
	"FISCAL_YEAR" NUMBER(4,0), 
 	"UNDERRECOVERY_OF_IDC"  NUMBER(12,2), 
	"APPLICABLE_IDC_RATE" NUMBER(6,3),
	"ON_CAMPUS_FLAG" CHAR(1), 
	"SOURCE_ACCOUNT" VARCHAR2(32),
	
	"UPDATE_TIMESTAMP" DATE constraint EPS_PROP_IDC_RATE_UT NOT NULL ENABLE, 
	"UPDATE_USER" VARCHAR2(60) constraint EPS_PROP_IDC_RATE_UU NOT NULL ENABLE, 
	"VER_NBR" NUMBER(8,0) DEFAULT 1  constraint  EPS_PROP_IDC_RATE_VN  NOT NULL ENABLE,
	"OBJ_ID" VARCHAR2(36) DEFAULT SYS_GUID()  constraint  EPS_PROP_IDC_RATE_OI  NOT NULL ENABLE,
	
	CONSTRAINT "PK_EPS_PROP_IDC_RATE_KRA" PRIMARY KEY ("PROPOSAL_NUMBER", "BUDGET_VERSION_NUMBER", "UNRECOVERED_FNA_ID") ENABLE
);

ALTER TABLE EPS_PROP_IDC_RATE ADD CONSTRAINT "FK_EPS_PROP_IDC_RATE_KRA" FOREIGN KEY ("PROPOSAL_NUMBER", "BUDGET_VERSION_NUMBER")
	  REFERENCES "BUDGET" ("PROPOSAL_NUMBER", "VERSION_NUMBER");
	 

	 