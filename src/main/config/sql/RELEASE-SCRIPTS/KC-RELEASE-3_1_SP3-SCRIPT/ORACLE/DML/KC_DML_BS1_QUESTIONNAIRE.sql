INSERT INTO QUESTIONNAIRE (QUESTIONNAIRE_REF_ID, QUESTIONNAIRE_ID, SEQUENCE_NUMBER, NAME, DESCRIPTION, UPDATE_USER, UPDATE_TIMESTAMP, IS_FINAL, VER_NBR, OBJ_ID, FILE_NAME, DOCUMENT_NUMBER) VALUES (SEQ_QUESTIONNAIRE_REF_ID.NEXTVAL, SEQ_QUESTIONNAIRE_ID.NEXTVAL, 1, 'Proposal Person Certification', 'Questions to verify the proposal person is acceptable for the proposal development document.', 'admin', SYSDATE, 'N', 1, SYS_GUID(), NULL, NULL);

update questionnaire set IS_FINAL = 'Y' where name = 'Proposal Person Certification';