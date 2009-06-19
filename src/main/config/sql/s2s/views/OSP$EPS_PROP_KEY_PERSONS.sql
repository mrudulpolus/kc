CREATE OR REPLACE VIEW OSP$EPS_PROP_KEY_PERSONS (
       PROPOSAL_NUMBER, PERSON_ID, PERSON_NAME, PROJECT_ROLE, FACULTY_FLAG,
       NON_MIT_PERSON_FLAG, PERCENTAGE_EFFORT, UPDATE_TIMESTAMP, UPDATE_USER) 
       AS select
       PROPOSAL_NUMBER, decode(PERSON_ID,null,to_char(ROLODEX_ID),PERSON_ID) PERSON_ID,
       FULL_NAME, PRIMARY_TITLE, IS_FACULTY,decode(PERSON_ID,null,'N','Y') NON_MIT_PERSON_FLAG, 
       decode(IS_OSC,'Y','999.00',PERCENTAGE_EFFORT) PERCENTAGE_EFFORT, 
       UPDATE_TIMESTAMP, UPDATE_USER
       from EPS_PROP_PERSON WHERE PROP_PERSON_ROLE_ID='KP';

