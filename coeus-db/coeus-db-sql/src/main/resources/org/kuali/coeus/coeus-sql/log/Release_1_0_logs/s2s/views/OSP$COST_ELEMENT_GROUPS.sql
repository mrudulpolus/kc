create or replace view OSP$COST_ELEMENT_GROUPS as 
	select MAPPING_NAME,COST_ELEMENT,COST_ELEMENT_GROUP,UPDATE_USER,UPDATE_TIMESTAMP
	from COST_ELEMENT_GROUPS;