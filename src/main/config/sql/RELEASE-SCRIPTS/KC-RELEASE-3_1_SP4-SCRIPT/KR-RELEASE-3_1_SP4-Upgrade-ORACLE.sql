set define off
set sqlblanklines on
spool KR-RELEASE-3_1_SP4-Upgrade-ORACLE-Install.log
@ORACLE/DML/KR_DML_BS1_KRIM_PERM_T.sql
@ORACLE/DML/KR_DML_BS1_KRNS_PARM_T.sql
@ORACLE/DML/KR_DML_BS2_KRIM_ROLE_PERM_T.sql
commit;
exit
