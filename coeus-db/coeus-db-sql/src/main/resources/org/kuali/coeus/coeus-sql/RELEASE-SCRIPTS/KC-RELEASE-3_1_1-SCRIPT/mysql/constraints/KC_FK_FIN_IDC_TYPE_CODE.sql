DELIMITER /
ALTER TABLE RATE_TYPE
  ADD INDEX IX_RATE_CLASS_CODE (RATE_CLASS_CODE)
/
ALTER TABLE RATE_TYPE 
  ADD INDEX IX_RATE_TYPE_CODE (RATE_TYPE_CODE)
/
ALTER TABLE FIN_IDC_TYPE_CODE
  ADD CONSTRAINT FK_IDC_RATE_TYPE_CODE FOREIGN KEY (RATE_TYPE_CODE, RATE_CLASS_CODE)
  REFERENCES RATE_TYPE (RATE_TYPE_CODE, RATE_CLASS_CODE)
/
DELIMITER ;
