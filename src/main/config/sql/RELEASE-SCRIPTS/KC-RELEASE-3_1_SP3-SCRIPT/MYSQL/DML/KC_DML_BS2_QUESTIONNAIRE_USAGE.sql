INSERT INTO SEQ_QUESTIONNAIRE_REF_ID VALUES (NULL);
INSERT INTO QUESTIONNAIRE_USAGE (QUESTIONNAIRE_USAGE_ID, MODULE_ITEM_CODE, MODULE_SUB_ITEM_CODE, QUESTIONNAIRE_REF_ID_FK, QUESTIONNAIRE_SEQUENCE_NUMBER, RULE_ID, QUESTIONNAIRE_LABEL, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR, OBJ_ID, IS_MANDATORY) VALUES (last_insert_id(), (SELECT MODULE_CODE FROM COEUS_MODULE WHERE DESCRIPTION = 'Development Proposal'), 3, (SELECT QUESTIONNAIRE_REF_ID FROM QUESTIONNAIRE WHERE NAME = 'Proposal Person Certification'), 1, 0, 'Proposal Person Certification', NOW(), 'admin', 1, UUID(), 'Y');