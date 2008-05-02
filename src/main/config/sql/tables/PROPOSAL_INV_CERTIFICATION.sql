#
# $Id: PROPOSAL_INV_CERTIFICATION.sql,v 1.2 2008-05-02 16:34:38 dbarre Exp $
#
create table PROPOSAL_INV_CERTIFICATION(
  PROPOSAL_NUMBER      NUMBER(12)   CONSTRAINT PROPOSAL_INV_CERTIFICATION_N1 not null,
  PROP_PERSON_NUMBER   NUMBER(12)   CONSTRAINT PROPOSAL_INV_CERTIFICATION_N2 NOT NULL,
  CERTIFIED_FLAG       CHAR(1),
  DATE_CERTIFIED       DATE,
  DATE_RECEIVED_BY_OSP DATE,
  update_timestamp  date         not null,
  update_user       varchar2 (60) not null,
  VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL,
  OBJ_ID VARCHAR2(36) DEFAULT SYS_GUID() NOT NULL,
  constraint PROPOSAL_INV_CERTIFICATION_N3
  primary key (PROPOSAL_NUMBER, PROP_PERSON_NUMBER)
);

ALTER TABLE PROPOSAL_INV_CERTIFICATION
  ADD ( CONSTRAINT PROPOSAL_INV_CERTIFICATION_C0
      UNIQUE (OBJ_ID) 
      NOT DEFERRABLE INITIALLY IMMEDIATE);