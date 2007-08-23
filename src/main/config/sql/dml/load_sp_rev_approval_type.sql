

INSERT INTO SP_REV_APPROVAL_TYPE(APPROVAL_TYPE_CODE, DESCRIPTION, UPDATE_TIMESTAMP, UPDATE_USER)
  VALUES(1, 'Pending', sysdate, user);

INSERT INTO SP_REV_APPROVAL_TYPE(APPROVAL_TYPE_CODE, DESCRIPTION, UPDATE_TIMESTAMP, UPDATE_USER)
  VALUES(2, 'Approved', sysdate, user);

INSERT INTO SP_REV_APPROVAL_TYPE(APPROVAL_TYPE_CODE, DESCRIPTION, UPDATE_TIMESTAMP, UPDATE_USER)
  VALUES(3, 'Not yet applied', sysdate, user);

INSERT INTO SP_REV_APPROVAL_TYPE(APPROVAL_TYPE_CODE, DESCRIPTION, UPDATE_TIMESTAMP, UPDATE_USER)
  VALUES(4, 'Exempt', sysdate, user);

INSERT INTO SP_REV_APPROVAL_TYPE(APPROVAL_TYPE_CODE, DESCRIPTION, UPDATE_TIMESTAMP, UPDATE_USER)
  VALUES(5, 'Link to IRB', sysdate, user);

