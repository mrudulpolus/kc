delimiter /
TRUNCATE TABLE PROPOSAL_LOG_STATUS
/
INSERT INTO PROPOSAL_LOG_STATUS (DESCRIPTION,OBJ_ID,PROPOSAL_LOG_STATUS_CODE,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR)
  VALUES ('Pending','73171DFFEB2E84ACE0404F8189D811BA','1',STR_TO_DATE( '20090908000000', '%Y%m%d%H%i%s' ),'KRADBA',1)
/
INSERT INTO PROPOSAL_LOG_STATUS (DESCRIPTION,OBJ_ID,PROPOSAL_LOG_STATUS_CODE,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR)
  VALUES ('Merged','73171DFFEB2F84ACE0404F8189D811BA','2',STR_TO_DATE( '20090908000000', '%Y%m%d%H%i%s' ),'KRADBA',1)
/
INSERT INTO PROPOSAL_LOG_STATUS (DESCRIPTION,OBJ_ID,PROPOSAL_LOG_STATUS_CODE,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR)
  VALUES ('Submitted','73171DFFEB3084ACE0404F8189D811BA','3',STR_TO_DATE( '20090908000000', '%Y%m%d%H%i%s' ),'KRADBA',1)
/
INSERT INTO PROPOSAL_LOG_STATUS (DESCRIPTION,OBJ_ID,PROPOSAL_LOG_STATUS_CODE,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR)
  VALUES ('Void','73171DFFEB3184ACE0404F8189D811BA','4',STR_TO_DATE( '20090908000000', '%Y%m%d%H%i%s' ),'KRADBA',1)
/
INSERT INTO PROPOSAL_LOG_STATUS (DESCRIPTION,PROPOSAL_LOG_STATUS_CODE,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR)
  VALUES ('Temporary','5',STR_TO_DATE( '20100426164411', '%Y%m%d%H%i%s' ),'KRADBA',1)
/
delimiter ;
