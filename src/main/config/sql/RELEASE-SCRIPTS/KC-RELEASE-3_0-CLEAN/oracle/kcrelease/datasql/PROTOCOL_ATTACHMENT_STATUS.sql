TRUNCATE TABLE PROTOCOL_ATTACHMENT_STATUS DROP STORAGE
/
INSERT INTO PROTOCOL_ATTACHMENT_STATUS (DESCRIPTION,OBJ_ID,STATUS_CD,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR)
  VALUES ('Incomplete','680A0BB6AD9368B4E0404F8189D82F8D','1',TO_DATE( '20090421000000', 'YYYYMMDDHH24MISS' ),'KRADBA',1)
/
INSERT INTO PROTOCOL_ATTACHMENT_STATUS (DESCRIPTION,OBJ_ID,STATUS_CD,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR)
  VALUES ('Complete','680A0BB6AD9468B4E0404F8189D82F8D','2',TO_DATE( '20090421000000', 'YYYYMMDDHH24MISS' ),'KRADBA',1)
/
