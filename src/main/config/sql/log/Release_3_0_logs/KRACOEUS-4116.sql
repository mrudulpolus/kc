ALTER TABLE EPS_PROPOSAL DROP CONSTRAINT FK_POST_SUB_STATUS_KRA;
ALTER TABLE EPS_PROPOSAL DROP COLUMN POST_SUB_STATUS_CODE;
drop table EPS_PROP_POST_SUB_STATUS;