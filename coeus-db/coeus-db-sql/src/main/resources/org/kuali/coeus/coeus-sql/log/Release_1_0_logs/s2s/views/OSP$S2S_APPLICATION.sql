create or replace view OSP$S2S_APPLICATION as 
	select PROPOSAL_NUMBER,APPLICATION,UPDATE_TIMESTAMP,UPDATE_USER
	from S2S_APPLICATION;