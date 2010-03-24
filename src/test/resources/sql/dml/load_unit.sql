Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('IN-PERS', 'PED-EMERGENCY ROOM SERVICES', 'IN-PED', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('BL-IIDC', 'IND INST ON DISABILITY/COMMNTY', 'BL-RCEN', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('BL-RCEN', 'RESEARCH CENTERS', 'BL-RUGS', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('BL-RUGS', 'OFFICE OF VP FOR RESEARCH', 'BL-BL', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');

Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, ORGANIZATION_ID, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('000001', 'University', '000001', null, to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('IN-CARD', 'CARDIOLOGY', 'IN-MDEP', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('IN-CARR', 'CARDIOLOGY RECHARGE CTR', 'IN-CARD', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('IN-MDEP', 'MEDICINE DEPT', 'IN-MED', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('IU-UNIV', 'UNIVERSITY LEVEL', '000001', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('BL-BL', 'BLOOMINGTON CAMPUS', 'IU-UNIV', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');

Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('IN-MED', 'SCHOOL OF MEDICINE', 'IN-IN', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('IN-PED', 'PEDIATRICS', 'IN-MED', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
Insert into UNIT
(UNIT_NUMBER, UNIT_NAME, PARENT_UNIT_NUMBER, UPDATE_TIMESTAMP, UPDATE_USER)
Values
('IN-IN', 'IND UNIV-PURDUE UNIV INDPLS', 'IU-UNIV', to_date('2010-01-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),'admin');
