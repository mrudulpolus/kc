DELIMITER /

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID)
VALUES ('KC-IACUC', 'Document', 'IACUC_DISPLAY_REVIEWER_NAME_TO_PROTOCOL_PERSONNEL', UUID(), 1, 'CONFG', '1', 'Display Reviewer Name to Protocol Personnel', 'A', 'KC')
/

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID)
VALUES ('KC-IACUC', 'Document', 'IACUC_DISPLAY_REVIEWER_NAME_TO_ACTIVE_COMMITTEE_MEMBERS', UUID(), 1, 'CONFG', '1', 'Display Reviewer Name to Active Committee Members', 'A', 'KC')
/

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID)
VALUES ('KC-IACUC', 'Document', 'IACUC_DISPLAY_REVIEWER_NAME_TO_REVIEWERS', UUID(), 1, 'CONFG', '1', 'Display Reviewer Name to Primary and Secondary Reviewers', 'A', 'KC')
/

DELIMITER ;
