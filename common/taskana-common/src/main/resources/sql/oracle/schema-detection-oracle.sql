ALTER SESSION SET CURRENT_SCHEMA = %schemaName%;

SELECT MAX(VERSION) FROM TASKANA_SCHEMA_VERSION;