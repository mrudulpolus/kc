DELIMITER /
ALTER TABLE PMC_PROTOCOL
  ADD CONSTRAINT FK_PMC_PROTOCOL_ID
  FOREIGN KEY (PERSON_MASS_CHANGE_ID) REFERENCES PERSON_MASS_CHANGE (PERSON_MASS_CHANGE_ID)
/
DELIMITER ;
